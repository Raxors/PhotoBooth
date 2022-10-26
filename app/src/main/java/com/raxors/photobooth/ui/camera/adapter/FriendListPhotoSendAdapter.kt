package com.raxors.photobooth.ui.camera.adapter

import com.raxors.photobooth.base.adapter.BasePagingAdapter
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.ui.friends.incoming.adapter.IncomingListDelegate

class FriendListPhotoSendAdapter(
    checkFriend : (friend: CheckFriendModel) -> Unit = {},
    checkAll : () -> Unit = {}
) : BasePagingAdapter(
    listOf(
        FriendListPhotoSendDelegate(
            checkFriend = checkFriend
        ),
        FriendListPhotoSendAllDelegate(
            checkAll = checkAll
        )
    )
)