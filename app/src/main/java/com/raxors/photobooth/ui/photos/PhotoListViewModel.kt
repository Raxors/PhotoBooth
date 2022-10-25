package com.raxors.photobooth.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.data.repository.image.PhotoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val repo: PhotoListRepository
): BaseViewModel() {


    private val _photoList = MutableLiveData<PagingData<PhotoResponse>>()

//    val detailedPhoto = MutableLiveData<Int>()

//    fun openPhoto(photo: PhotoResponse){
//        val position = photoList.value?.indexOf(photo)
//        position?.let { detailedPhoto.postValue(it) }
//    }

//    fun getPhotoList() {
//        if (_photoList.value.isNullOrEmpty())
//            launch({
//                _photoList.postValue(repo.getPhotoList())
//            })
//    }

    suspend fun getPhotoList(): LiveData<PagingData<PhotoResponse>> {
        val response = repo.getPhotoList().cachedIn(viewModelScope)
        _photoList.value = response.value
        return response
    }

}