package com.raxors.photobooth.ui.friends.incoming.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.ProfileResponse
import com.raxors.photobooth.databinding.ItemIncomingBinding

class IncomingListAdapter(
    val declineRequest : (userId: String) -> Unit = {}
) : PagingDataAdapter<ProfileResponse, IncomingListViewHolder>(ProfileResponse.Companion.DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomingListViewHolder {
        return IncomingListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_incoming, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IncomingListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, declineRequest) }
    }
}

class IncomingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemIncomingBinding.bind(itemView)
    fun bind(item: ProfileResponse, declineRequest : (userId: String) -> Unit = {}) = with(binding) {
        Glide.with(itemView).load(item.imagePath).placeholder(R.mipmap.bloom).into(ivPhoto)
        tvUsername.text = item.username
        btnDecline.setOnClickListener {
            item.id?.let { userId -> declineRequest(userId) }
        }
    }
}