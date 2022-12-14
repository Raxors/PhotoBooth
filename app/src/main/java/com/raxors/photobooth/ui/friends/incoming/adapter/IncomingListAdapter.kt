package com.raxors.photobooth.ui.friends.incoming.adapter

import com.raxors.photobooth.base.adapter.BasePagingAdapter
import com.raxors.photobooth.data.model.response.ProfileResponse

class IncomingListAdapter(
    addFriend : (user: ProfileResponse) -> Unit = {},
    declineRequest : (user: ProfileResponse) -> Unit = {}
) : BasePagingAdapter(
    listOf(
        IncomingListDelegate(
            addFriend = addFriend,
            declineRequest = declineRequest
        )
    )
)