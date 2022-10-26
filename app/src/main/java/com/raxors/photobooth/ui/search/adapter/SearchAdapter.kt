package com.raxors.photobooth.ui.search.adapter

import com.raxors.photobooth.base.adapter.BaseAdapter
import com.raxors.photobooth.data.model.response.ProfileResponse

class SearchAdapter(
    addFriend : (user: ProfileResponse) -> Unit = {}
) : BaseAdapter(
    listOf(
        SearchDelegate(
            addFriend = addFriend
        )
    )
)