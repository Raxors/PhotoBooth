package com.raxors.photobooth.data.repository.image

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.raxors.photobooth.data.api.PhotoBoothApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoListRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi
) : PhotoListRepository {
    override suspend fun getPhotoList() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_PHOTO,
                enablePlaceholders = true,
                initialLoadSize = PAGE_SIZE_PHOTO
            ),
            pagingSourceFactory = {
                PhotosDataSource(api)
            }
        ).liveData
}