package com.raxors.photobooth.ui.friends.outgoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.request.RemoveFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OutgoingViewModel @Inject constructor(
    private val friendRepo: FriendsRepository
): BaseViewModel() {

    val outgoingList = MutableLiveData<PagingData<BaseModel>>()
    val removedFriend = MutableLiveData<ProfileResponse>(null)

    suspend fun getOutgoingList(): LiveData<PagingData<BaseModel>> {
        val response = friendRepo.getOutgoingRequests().cachedIn(viewModelScope)
        outgoingList.value = response.value
        return response
    }

    fun removeFriend(user: ProfileResponse) {
        user.id?.let {
            launch({
                friendRepo.removeFriend(RemoveFriendRequest(it))
                removedFriend.postValue(user)
            })
        }
    }

}