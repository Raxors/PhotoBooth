package com.raxors.photobooth.base.adapter

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T: BaseModel>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.isIdDiff(newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.isContentDiff(newItem)
    override fun getChangePayload(oldItem: T, newItem: T) = oldItem.getPayloadDiff(newItem)
}