package com.raxors.photobooth.ui.main

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.request.PhotoRequest
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import com.raxors.photobooth.data.repository.image.PhotoRepository
import com.raxors.photobooth.ui.camera.adapter.CheckFriendModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val photoRepository: PhotoRepository
) : BaseViewModel() {

    val friendList = MutableLiveData<PagingData<BaseModel>>()
    val isPhotoSended = MutableLiveData<Boolean>()

    val navigate = MutableLiveData<Navigate>()

    val bitmap = MutableLiveData<Bitmap>()
    val base64 = MutableLiveData<String>()

    suspend fun getFriends(): LiveData<PagingData<BaseModel>> {
        val response =
            friendsRepository.getFriends().map { it.map { CheckFriendModel(it) as BaseModel } }
                .cachedIn(viewModelScope)
        friendList.value = response.value
        return response
    }

    fun openPhotoSend(bitmap: Bitmap, base64: String) {
        this.bitmap.postValue(bitmap)
        this.base64.postValue(base64)
        navigate.postValue(Navigate.ToPhotoSend)
    }

    fun sendPhoto(listIds: List<String>) {
        launch({
            photoRepository.sendPhoto(
                PhotoRequest(
                    base64.value ?: "",
                    listIds
                )
            )
            isPhotoSended.postValue(true)
        })
    }

    fun resetSending() {
        isPhotoSended.postValue(false)
    }

}

sealed class Navigate {
    object ToPhotoSend : Navigate()
}