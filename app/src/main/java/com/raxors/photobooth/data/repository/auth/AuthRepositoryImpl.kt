package com.raxors.photobooth.data.repository.auth

import com.raxors.photobooth.data.api.AuthApi
import com.raxors.photobooth.data.model.request.LoginRequest
import com.raxors.photobooth.data.model.request.RegistrationRequest
import com.raxors.photobooth.data.model.response.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
): AuthRepository {

    override suspend fun registration(registrationRequest: RegistrationRequest): LoginResponse =
        api.registration(registrationRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        api.login(loginRequest)

    override suspend fun refreshAuthToken(token: LoginResponse): LoginResponse =
        api.refreshAuthToken("${token.tokenType} ${token.refreshToken}")

}