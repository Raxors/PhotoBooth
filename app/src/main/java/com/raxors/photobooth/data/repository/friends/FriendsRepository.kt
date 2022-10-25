package com.raxors.photobooth.data.repository.friends

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse

interface FriendsRepository {

    suspend fun addFriend(addFriendRequest: AddFriendRequest)

    suspend fun getFriends(): LiveData<PagingData<ProfileResponse>>

    suspend fun getIncomingRequests(): LiveData<PagingData<ProfileResponse>>

}