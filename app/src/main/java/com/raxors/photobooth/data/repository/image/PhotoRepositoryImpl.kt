package com.raxors.photobooth.data.repository.image

import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.request.PhotoRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi
): PhotoRepository {

    override suspend fun sendPhoto(photoRequest: PhotoRequest) =
        api.sendPhoto(photoRequest)

}