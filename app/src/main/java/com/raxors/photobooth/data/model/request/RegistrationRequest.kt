package com.raxors.photobooth.data.model.request

data class RegistrationRequest(
    val username: String,
    val password: String,
    val email: String
)