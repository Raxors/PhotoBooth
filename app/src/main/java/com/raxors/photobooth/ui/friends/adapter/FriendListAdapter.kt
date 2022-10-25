package com.raxors.photobooth.ui.friends.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemFriendBinding
import com.raxors.photobooth.base.Extensions.Companion.hide
import com.raxors.photobooth.base.Extensions.Companion.show
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.di.BASE_URL

class FriendListAdapter(
    val removeFriend : (user: ProfileResponse) -> Unit = {}
) : PagingDataAdapter<ProfileResponse, FriendListViewHolder>(ProfileResponse.Companion.DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, removeFriend) }
    }
}

class FriendListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemFriendBinding.bind(itemView)
    fun bind(item: ProfileResponse, removeFriend : (user: ProfileResponse) -> Unit = {}) = with(binding) {
        Glide.with(itemView).load(BASE_PHOTO_URL + item.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
        if (item.isFriend) {
            btnRemoveFriend.hide()
        } else {
            btnRemoveFriend.show()
        }
        tvUsername.text = item.username
        btnRemoveFriend.setOnClickListener {
            removeFriend(item)
        }
    }
}