package com.raxors.photobooth.data.repository.profile

import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.domain.model.SearchModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi
): ProfileRepository {

    override suspend fun getProfile(): ProfileResponse =
        api.getProfile()

    override suspend fun getProfileById(userId: String): ProfileResponse =
        api.getProfileById(userId)

    override suspend fun searchUser(username: String) =
        SearchModel(api.searchUser(username))

}