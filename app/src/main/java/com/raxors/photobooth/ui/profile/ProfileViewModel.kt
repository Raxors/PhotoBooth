package com.raxors.photobooth.ui.profile

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepository
): BaseViewModel() {

    fun getProfile() {
        launch({
            profileRepo.getProfile()
        })
    }

}