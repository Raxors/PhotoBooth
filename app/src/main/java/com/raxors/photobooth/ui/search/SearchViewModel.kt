package com.raxors.photobooth.ui.search

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseScreenState
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import com.raxors.photobooth.domain.model.SearchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val friendsRepo: FriendsRepository,
    private val profileRepo: ProfileRepository
) : BaseViewModel() {

    val userList = MutableLiveData<MutableList<BaseModel>>()
    val isAdded = MutableLiveData<Boolean>(false)

    fun searchUser(username: String) {
        launch({
            userList.postValue(mutableListOf(profileRepo.searchUser(username)))
        }, {
            baseScreenState.postValue(BaseScreenState.Error(it))
        })
    }

    fun addFriend(userId: String) {
        launch({
            friendsRepo.addFriend(AddFriendRequest(userId))
            isAdded.postValue(true)
        })
    }

}