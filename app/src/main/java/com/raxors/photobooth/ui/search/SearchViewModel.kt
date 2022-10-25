package com.raxors.photobooth.ui.search

import com.raxors.photobooth.base.BaseViewModel
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: FriendsRepository
): BaseViewModel() {



}