package com.raxors.photobooth.ui.friends.outgoing.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemFriendBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.domain.model.OutgoingListModel

class OutgoingListDelegate(
    val declineRequest: (user: ProfileResponse) -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        OutgoingListViewHolder(
            parent,
            declineRequest = declineRequest
        )

    override fun isValidType(model: BaseModel): Boolean = model is OutgoingListModel

    inner class OutgoingListViewHolder(
        val parent: ViewGroup,
        val declineRequest: (user: ProfileResponse) -> Unit = {}
    ) : BaseViewHolder(parent, R.layout.item_friend) {

        lateinit var binding: ItemFriendBinding

        override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
            binding = ItemFriendBinding.bind(itemView)
            with(binding) {
                model as OutgoingListModel
                Glide.with(itemView).load(BASE_PHOTO_URL + model.user.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
                tvUsername.text = model.user.username
                btnRemoveFriend.setOnClickListener {
                    declineRequest(model.user)
                }
            }
        }
    }

}

