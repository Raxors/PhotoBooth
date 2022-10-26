package com.raxors.photobooth.ui.camera.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemFriendSendBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL

class FriendListPhotoSendDelegate(
    val checkFriend: (user: CheckFriendModel) -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        IncomingListViewHolder(
            parent,
            checkFriend = checkFriend
        )

    override fun isValidType(model: BaseModel): Boolean = model is CheckFriendModel

    inner class IncomingListViewHolder(
        val parent: ViewGroup,
        val checkFriend: (user: CheckFriendModel) -> Unit = {}
    ) : BaseViewHolder(parent, R.layout.item_friend_send) {

        lateinit var binding: ItemFriendSendBinding

        override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
            binding = ItemFriendSendBinding.bind(itemView)
            with(binding) {
                model as CheckFriendModel
                if (model.isChecked)
                    ivPhoto.setBackgroundResource(R.drawable.blue_circle_background)
                else
                    ivPhoto.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                Glide.with(itemView).load(BASE_PHOTO_URL + model.user.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
                tvUsername.text = model.user.username
                itemView.setOnClickListener {
                    checkFriend(model)
                }
            }
        }
    }

}

data class CheckFriendModel(
    val user: ProfileResponse,
    var isChecked: Boolean = false
): BaseModel {

    override fun isContentDiff(other: BaseModel): Boolean {
        return other is CheckAllModel && other.isChecked == this.isChecked
    }
}

