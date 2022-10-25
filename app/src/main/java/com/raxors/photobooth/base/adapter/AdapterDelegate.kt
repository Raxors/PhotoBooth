package com.raxors.photobooth.base.adapter

import android.view.ViewGroup
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder

interface AdapterDelegate {

    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun isValidType(postModel: BaseModel): Boolean
}