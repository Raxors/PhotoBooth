package com.raxors.photobooth.data.repository.profile

import com.raxors.photobooth.data.model.response.ProfileResponse

interface ProfileRepository {

    suspend fun getProfile(): ProfileResponse

    suspend fun searchUser(username: String): ProfileResponse

}