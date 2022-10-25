package com.raxors.photobooth.data.repository.friends

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.request.RemoveFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import com.raxors.photobooth.domain.model.IncomingListModel
import com.raxors.photobooth.utils.LIMIT_DEFAULT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRepositoryImpl @Inject constructor(
    private val api: PhotoBoothApi,
    private val prefsRepository: PrefsRepository,

): FriendsRepository {

    override suspend fun addFriend(addFriendRequest: AddFriendRequest) =
        api.addFriend(addFriendRequest)

    override suspend fun removeFriend(removeFriendRequest: RemoveFriendRequest) {
        api.removeFriend(removeFriendRequest)
    }

    override suspend fun getFriends() =
        Pager(
            config = PagingConfig(
                pageSize = LIMIT_DEFAULT,
                enablePlaceholders = true,
                initialLoadSize = LIMIT_DEFAULT
            ),
            pagingSourceFactory = {
                FriendsDataSource(api)
            }
        ).liveData

    override suspend fun getIncomingRequests(): LiveData<PagingData<BaseModel>> =
        Pager(
            config = PagingConfig(
                pageSize = LIMIT_DEFAULT,
                enablePlaceholders = true,
                initialLoadSize = LIMIT_DEFAULT
            ),
            pagingSourceFactory = {
                IncomingListDataSource(api)
            }
        ).liveData

    override suspend fun getOutgoingRequests(): LiveData<PagingData<BaseModel>> =
        Pager(
            config = PagingConfig(
                pageSize = LIMIT_DEFAULT,
                enablePlaceholders = true,
                initialLoadSize = LIMIT_DEFAULT
            ),
            pagingSourceFactory = {
                OutgoingListDataSource(api)
            }
        ).liveData

}