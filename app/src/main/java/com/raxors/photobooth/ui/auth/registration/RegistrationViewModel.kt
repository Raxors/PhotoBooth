package com.raxors.photobooth.ui.auth.registration

import androidx.lifecycle.MutableLiveData
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.request.RegistrationRequest
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.repository.auth.AuthRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository
): BaseViewModel() {

    val regResult = MutableLiveData<LoginResponse>()

    fun registration(registrationRequest: RegistrationRequest) {
        launch({
            val login = authRepository.registration(registrationRequest)
            regResult.postValue(login)
            prefsRepository.saveLoginInfo(login)
        })
    }

}