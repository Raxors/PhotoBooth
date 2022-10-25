package com.raxors.photobooth.ui.viewholders

import android.view.ViewGroup
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder

class LoaderViewHolder(val parent: ViewGroup) : BaseViewHolder(parent, R.layout.item_loader) {

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
    }
}

class Loader: BaseModel

class LoaderDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        LoaderViewHolder(parent)

    override fun isValidType(model: BaseModel): Boolean = model is Loader
}