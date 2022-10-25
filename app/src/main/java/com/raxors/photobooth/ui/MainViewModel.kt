package com.raxors.photobooth.ui

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val profileRepository: ProfileRepository,
    private val friendsRepository: FriendsRepository
): BaseViewModel() {

    val friendList = MutableLiveData<List<ProfileResponse>>()

    init {
//        launch({
//            val profile = profileRepository.getProfile()
//            profile.id?.let { prefsRepository.saveUserId(it) }
//        })
    }

    val isLogged = MutableLiveData<Boolean>()

    fun logout() {
        prefsRepository.clearLoginInfo()
        isLogged.postValue(false)
    }

    fun login() {
        isLogged.postValue(true)
    }

    fun getFriends() {
//        launch({
//            friendList.postValue(friendsRepository.getFriends())
//        })
    }

    fun getLoginInfo(): LoginResponse? = prefsRepository.getLoginInfo()

}