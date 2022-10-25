package com.raxors.photobooth.ui.auth.login

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.request.LoginRequest
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.repository.auth.AuthRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository
): BaseViewModel() {

    val loginResult = MutableLiveData<LoginResponse>()

    fun login(loginRequest: LoginRequest) {
        launch({
            val login = authRepository.login(loginRequest)
            loginResult.postValue(login)
            prefsRepository.saveLoginInfo(login)
        })
    }

}