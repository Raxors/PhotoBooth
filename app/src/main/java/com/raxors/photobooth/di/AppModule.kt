package com.raxors.photobooth.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kernel.finch.networklog.okhttp.FinchOkHttpLogger
import com.raxors.photobooth.base.annotation.AuthOkHttpClientQualifier
import com.raxors.photobooth.base.annotation.CommonOkHttpClientQualifier
import com.raxors.photobooth.base.annotation.isAuthorizationRequired
import com.raxors.photobooth.data.api.AuthApi
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.model.response.isValid
import com.raxors.photobooth.data.repository.auth.AuthRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val TAG = "ZALOOPA"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        prefsRepo: Lazy<PrefsRepository>,
        authRepo: AuthRepository
    ): Authenticator = Authenticator { _, response ->
        val request = response.request
        if (!request.isAuthorizationRequired())
            return@Authenticator null
        Log.d(TAG, Gson().toJson(prefsRepo.get().getLoginInfo()))
        val actualToken = prefsRepo.get().getLoginInfo().takeIf { it.isValid } ?: run {
            runBlocking { prefsRepo.get().clearLoginInfo() }
            return@Authenticator null
        }
        return@Authenticator synchronized(this) {
            val newToken: LoginResponse? =
                if (!actualToken.isValid || response.code == 401)
                    try {
                        runBlocking { authRepo.refreshAuthToken(actualToken) }
                    } catch (e: Exception) {
                        null
                    }
                else prefsRepo.get().getLoginInfo()
            Log.d(TAG, "4")
            newToken?.takeIf { it.isValid }?.let {
                request.newBuilder()
                    .header("Authorization", "${it.tokenType} ${it.accessToken}")
                    .build()
            }
        }
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(
        prefsRepo: PrefsRepository
    ): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newBuilder = request.newBuilder()

            if (request.isAuthorizationRequired()) {
                try {
                    runBlocking { prefsRepo.getLoginInfo() }.takeIf { it.isValid }
                        ?.let {
                            val type = it.tokenType
                            val accessToken = it.accessToken
                            newBuilder.header("Authorization", "$type $accessToken")
                        }
                } catch (e: Exception) {
                }
            }

            try {
                chain.proceed(newBuilder.build())
            } catch (e: HttpException) {
                throw e
            }
        }

    @Singleton
    @Provides
    @CommonOkHttpClientQualifier
    fun provideOkHttpClient(requestInterceptor: Interceptor, authenticator: Authenticator) =
        OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(requestInterceptor)
            .addInterceptor(FinchOkHttpLogger.logger as Interceptor)
            .build()

    @Singleton
    @Provides
    @AuthOkHttpClientQualifier
    fun provideAuthOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(FinchOkHttpLogger.logger as Interceptor)
        .build()


    @Singleton
    @Provides
    @CommonOkHttpClientQualifier
    fun provideRetrofitInstance(@CommonOkHttpClientQualifier okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @AuthOkHttpClientQualifier
    fun provideRetrofitAuthInstance(@AuthOkHttpClientQualifier okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideWebService(@CommonOkHttpClientQualifier retrofit: Retrofit): PhotoBoothApi =
        retrofit.create(PhotoBoothApi::class.java)

    @Singleton
    @Provides
    fun provideAuthService(@AuthOkHttpClientQualifier retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    /*@Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }*/
}

//const val BASE_URL = "http://192.168.0.126:8080/api/v1/"
const val BASE_URL = "https://photoboobs.herokuapp.com/api/v1/"
const val BASE_PHOTO_URL = "https://photoboobs.herokuapp.com"
