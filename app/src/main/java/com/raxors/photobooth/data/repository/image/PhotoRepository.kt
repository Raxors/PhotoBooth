package com.raxors.photobooth.data.repository.image

import com.raxors.photobooth.data.model.request.PhotoRequest

interface PhotoRepository {

    suspend fun sendPhoto(photoRequest: PhotoRequest)

}