package com.raxors.photobooth.data.repository.friends

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi,
    private val prefsRepository: PrefsRepository,

): FriendsRepository {

    override suspend fun addFriend(addFriendRequest: AddFriendRequest) =
        api.addFriend(addFriendRequest)

    override suspend fun getFriends() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                FriendsDataSource(api)
            }
        ).liveData

    override suspend fun getIncomingRequests(): LiveData<PagingData<ProfileResponse>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                FriendsDataSource(api)
            }
        ).liveData

}