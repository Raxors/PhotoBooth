package com.raxors.photobooth.data.repository.image

import com.raxors.photobooth.data.model.request.PhotoRequest
import com.raxors.photobooth.data.model.response.PhotoResponse

interface PhotoRepository {

    suspend fun sendPhoto(photoRequest: PhotoRequest)

    suspend fun getPhotoById(photoId: String): String

    suspend fun getLastImage(): PhotoResponse
}