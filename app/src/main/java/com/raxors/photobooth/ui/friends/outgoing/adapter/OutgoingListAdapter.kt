package com.raxors.photobooth.ui.friends.outgoing.adapter

import com.raxors.photobooth.base.adapter.BasePagingAdapter
import com.raxors.photobooth.data.model.response.ProfileResponse

class OutgoingListAdapter(
    declineRequest : (user: ProfileResponse) -> Unit = {}
) : BasePagingAdapter(
    listOf(
        OutgoingListDelegate(
            declineRequest = declineRequest
        )
    )
)