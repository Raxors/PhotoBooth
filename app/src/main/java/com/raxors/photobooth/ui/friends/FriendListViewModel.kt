package com.raxors.photobooth.ui.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.request.RemoveFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(
    private val profileRepo: ProfileRepository,
    private val friendRepo: FriendsRepository
): BaseViewModel() {


    val userList = MutableLiveData<List<ProfileResponse>>()
    val friendList = MutableLiveData<PagingData<ProfileResponse>>()
    val removedFriend = MutableLiveData<ProfileResponse>(null)

    fun setFriendList(list: List<ProfileResponse>) {
//        friendList.addAll(list)
    }

    fun removeFriend(user: ProfileResponse) {
        user.id?.let {
            launch({
                friendRepo.removeFriend(RemoveFriendRequest(it))
                removedFriend.postValue(user)
            })
        }
    }

    fun refresh() {

    }

    suspend fun getFriends(): LiveData<PagingData<ProfileResponse>> {
        val response = friendRepo.getFriends().cachedIn(viewModelScope)
        friendList.value = response.value
        return response
    }

}