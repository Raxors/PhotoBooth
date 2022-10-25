package com.raxors.photobooth.data.repository.friends

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.request.RemoveFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.domain.model.IncomingListModel

interface FriendsRepository {

    suspend fun addFriend(addFriendRequest: AddFriendRequest)

    suspend fun getFriends(): LiveData<PagingData<ProfileResponse>>

    suspend fun getIncomingRequests(): LiveData<PagingData<BaseModel>>

    suspend fun getOutgoingRequests(): LiveData<PagingData<BaseModel>>

    suspend fun removeFriend(removeFriendRequest: RemoveFriendRequest)

}