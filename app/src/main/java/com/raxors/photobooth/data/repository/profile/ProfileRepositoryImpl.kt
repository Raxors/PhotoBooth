package com.raxors.photobooth.data.repository.profile

import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.response.ProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi
): ProfileRepository {

    override suspend fun getProfile(): ProfileResponse =
        api.getProfile()

    override suspend fun searchUser(username: String) =
        api.searchUser(username)

}