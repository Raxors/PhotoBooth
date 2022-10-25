package com.raxors.photobooth.data.repository.friends

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.response.ProfileResponse

const val PAGE_SIZE = 20

class FriendsDataSource(
    private val apiService: PhotoBoothApi
): PagingSource<Int, ProfileResponse>() {

    override fun getRefreshKey(state: PagingState<Int, ProfileResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileResponse> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getFriends(position, params.loadSize)
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