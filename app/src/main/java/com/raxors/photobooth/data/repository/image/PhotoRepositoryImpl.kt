package com.raxors.photobooth.data.repository.image

import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.request.PhotoRequest
import com.raxors.photobooth.data.model.response.PhotoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi
): PhotoRepository {

    override suspend fun sendPhoto(photoRequest: PhotoRequest) =
        api.sendPhoto(photoRequest)

    override suspend fun getPhotoById(photoId: String): String =
        api.getPhotoById(photoId)

    override suspend fun getLastImage(): PhotoResponse =
        api.getLastImage()

}