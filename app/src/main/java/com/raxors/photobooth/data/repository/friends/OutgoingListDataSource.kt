package com.raxors.photobooth.data.repository.friends

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.domain.model.OutgoingListModel

class OutgoingListDataSource(
    private val apiService: PhotoBoothApi
): PagingSource<Int, BaseModel>() {

    override fun getRefreshKey(state: PagingState<Int, BaseModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaseModel> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getOutgoingRequests(position, params.loadSize).map { OutgoingListModel(it) }
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