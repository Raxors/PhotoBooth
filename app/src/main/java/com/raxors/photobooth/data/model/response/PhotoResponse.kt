package com.raxors.photobooth.data.model.response

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoResponse(
    val id: String?,
    val path: String?,
    val createdTime: String?,
    val ownerId: String?
): Parcelable {

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<PhotoResponse>() {

            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                oldItem == newItem
        }
    }
}