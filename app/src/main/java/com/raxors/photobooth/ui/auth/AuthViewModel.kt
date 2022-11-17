package com.raxors.photobooth.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.request.GoogleLoginRequest
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.repository.auth.AuthRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val authRepository: AuthRepository
): BaseViewModel() {

    fun isLogin() = prefsRepository.getLoginInfo() != null

    val loginResult = MutableLiveData<LoginResponse>()

    fun loginGoogle(token: String) {
        launch({
            val login = authRepository.googleLogin(GoogleLoginRequest(token))
            loginResult.postValue(login)
            prefsRepository.saveLoginInfo(login)
        })
    }

}