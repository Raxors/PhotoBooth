package com.raxors.photobooth.ui.photos.detail

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): BaseViewModel() {

    val profile = MutableLiveData<ProfileResponse>()

    fun getProfile(userId: String) {
        launch({
            profile.postValue(profileRepository.getProfileById(userId))
        })
    }

}