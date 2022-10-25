package com.raxors.photobooth.data.model.response

import androidx.recyclerview.widget.DiffUtil

data class PhotoResponse(
    val id: String?,
    val path: String?,
    val createdTime: String?,
    val ownerId: String?
) {

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<PhotoResponse>() {

            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                oldItem == newItem
        }
    }
}