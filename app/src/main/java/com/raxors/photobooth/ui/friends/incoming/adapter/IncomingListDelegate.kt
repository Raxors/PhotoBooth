package com.raxors.photobooth.ui.friends.incoming.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemIncomingBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.domain.model.IncomingListModel

class IncomingListDelegate(
    val addFriend: (user: ProfileResponse) -> Unit = {},
    val declineRequest: (user: ProfileResponse) -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        IncomingListViewHolder(
            parent,
            addFriend = addFriend,
            declineRequest = declineRequest
        )

    override fun isValidType(model: BaseModel): Boolean = model is IncomingListModel

    inner class IncomingListViewHolder(
        val parent: ViewGroup,
        val addFriend: (user: ProfileResponse) -> Unit = {},
        val declineRequest: (user: ProfileResponse) -> Unit = {}
    ) : BaseViewHolder(parent, R.layout.item_incoming) {

        lateinit var binding: ItemIncomingBinding

        override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
            binding = ItemIncomingBinding.bind(itemView)
            with(binding) {
                model as IncomingListModel
                Glide.with(itemView).load(BASE_PHOTO_URL + model.user.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
                tvUsername.text = model.user.username
                btnDecline.setOnClickListener {
                    declineRequest(model.user)
                }
                btnAdd.setOnClickListener {
                    addFriend(model.user)
                }
            }
        }
    }

}

