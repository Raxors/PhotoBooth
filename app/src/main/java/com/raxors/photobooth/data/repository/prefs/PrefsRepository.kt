package com.raxors.photobooth.data.repository.prefs

import com.raxors.photobooth.data.model.response.LoginResponse

interface PrefsRepository {

    fun getLoginInfo(): LoginResponse?

    fun saveLoginInfo(token: LoginResponse)

    fun clearLoginInfo()

    fun saveUserId(userId: String)

    fun getUserId(): String?

}
/*

data class LoginInfo(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresAt: String
)*/
