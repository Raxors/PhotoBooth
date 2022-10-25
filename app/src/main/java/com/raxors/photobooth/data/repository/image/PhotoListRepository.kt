package com.raxors.photobooth.data.repository.image

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.raxors.photobooth.data.model.response.PhotoResponse

interface PhotoListRepository {

    suspend fun getPhotoList(): LiveData<PagingData<PhotoResponse>>

}