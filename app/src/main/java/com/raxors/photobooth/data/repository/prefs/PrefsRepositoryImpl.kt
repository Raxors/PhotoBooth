package com.raxors.photobooth.data.repository.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.utils.Prefs.getParcelable
import com.raxors.photobooth.utils.Prefs.putParcelable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsRepositoryImpl @Inject constructor(
    @ApplicationContext context : Context
) : PrefsRepository {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getLoginInfo(): LoginResponse? =
        sharedPreferences.getParcelable("AUTH_TOKEN")

    override fun saveLoginInfo(token: LoginResponse) {
        sharedPreferences.edit()
            .putParcelable("AUTH_TOKEN", token)
            .apply()
    }

    override fun clearLoginInfo() {
        sharedPreferences.edit()
            .remove("AUTH_TOKEN")
            .apply()
    }

    override fun getUserId(): String? =
        sharedPreferences.getString("USER_ID", "")

    override fun saveUserId(userId: String) {
        sharedPreferences.edit()
            .putString("USER_ID", userId)
            .apply()
    }
}