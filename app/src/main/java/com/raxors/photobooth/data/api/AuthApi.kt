package com.raxors.photobooth.data.api

import com.raxors.photobooth.data.model.request.LoginRequest
import com.raxors.photobooth.data.model.request.RegistrationRequest
import com.raxors.photobooth.data.model.response.LoginResponse
import retrofit2.http.*

interface AuthApi {

    //Auth region
    @POST("auth/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): LoginResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/refreshToken")
    suspend fun refreshAuthToken(@Header("Authorization") token: String): LoginResponse
}