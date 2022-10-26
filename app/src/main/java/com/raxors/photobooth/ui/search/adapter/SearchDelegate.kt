package com.raxors.photobooth.ui.search.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemFriendBinding
import com.raxors.photobooth.databinding.ItemSearchBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.domain.model.SearchModel

class SearchDelegate(
    val addFriend: (user: ProfileResponse) -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        SearchViewHolder(
            parent,
            addFriend = addFriend
        )

    override fun isValidType(model: BaseModel): Boolean = model is SearchModel

    inner class SearchViewHolder(
        val parent: ViewGroup,
        val addFriend: (user: ProfileResponse) -> Unit = {}
    ) : BaseViewHolder(parent, R.layout.item_search) {

        lateinit var binding: ItemSearchBinding

        override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
            binding = ItemSearchBinding.bind(itemView)
            with(binding) {
                model as SearchModel
                Glide.with(itemView).load(BASE_PHOTO_URL + model.user.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
                tvUsername.text = model.user.username
                btnAddFriend.setOnClickListener {
                    addFriend(model.user)
                }
            }
        }
    }

}

