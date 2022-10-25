package com.raxors.photobooth.data.repository.image

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.response.PhotoResponse

const val PAGE_SIZE_PHOTO = 25

class PhotosDataSource(
    private val apiService: PhotoBoothApi
): PagingSource<Int, PhotoResponse>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getAllPhotos(position, params.loadSize)
            val nextKey = if (response.isEmpty()) null else position + 1
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}