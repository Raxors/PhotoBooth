package com.raxors.photobooth.ui.photos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.databinding.ItemPhotoBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL

class PhotoListAdapter(
    val openImage: (photo: PhotoResponse) -> Unit = {}
) : PagingDataAdapter<PhotoResponse, PhotoListViewHolder>(PhotoResponse.Companion.DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        return PhotoListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, openImage) }
    }
}

class PhotoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPhotoBinding.bind(itemView)
    fun bind(item: PhotoResponse, openImage: (photo: PhotoResponse) -> Unit = {}) = with(binding) {
        Glide.with(itemView).load(BASE_PHOTO_URL + item.path).into(ivPhoto)
        itemView.setOnClickListener {
            openImage(item)
        }
    }
}