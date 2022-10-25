package com.raxors.photobooth.data.repository.auth

import com.raxors.photobooth.data.model.request.LoginRequest
import com.raxors.photobooth.data.model.request.RegistrationRequest
import com.raxors.photobooth.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun registration(registrationRequest: RegistrationRequest): LoginResponse

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun refreshAuthToken(token: LoginResponse): LoginResponse

}