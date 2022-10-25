package com.raxors.photobooth.data.model.response

import androidx.recyclerview.widget.DiffUtil

data class ProfileResponse(
    val id: String?,
    val username: String?,
    val name: String?,
    val status: String?,
    val imagePath: String?,

    var isFriend: Boolean = false,
    var isChecked: Boolean = false
) {
    companion object {
        class DiffCallback : DiffUtil.ItemCallback<ProfileResponse>() {

            override fun areItemsTheSame(oldItem: ProfileResponse, newItem: ProfileResponse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProfileResponse, newItem: ProfileResponse) =
                oldItem == newItem
        }
    }
}
