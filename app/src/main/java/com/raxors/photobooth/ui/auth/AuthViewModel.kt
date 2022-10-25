package com.raxors.photobooth.ui.auth

import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
): BaseViewModel() {

    fun isLogin() = prefsRepository.getLoginInfo() != null

}