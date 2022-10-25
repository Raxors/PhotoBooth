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

class FriendListAdapter(
    val addFriend : (userId: String) -> Unit = {}
) : PagingDataAdapter<ProfileResponse, FriendListViewHolder>(ProfileResponse.Companion.DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, addFriend) }
    }
}

class FriendListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemFriendBinding.bind(itemView)
    fun bind(item: ProfileResponse, addFriend : (userId: String) -> Unit = {}) = with(binding) {
        Glide.with(itemView).load(item.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
        if (item.isFriend) {
            btnRemoveFriend.hide()
        } else {
            btnRemoveFriend.show()
        }
        tvUsername.text = item.username
        btnRemoveFriend.setOnClickListener {
            item.id?.let { userId -> addFriend(userId) }
        }
    }
}