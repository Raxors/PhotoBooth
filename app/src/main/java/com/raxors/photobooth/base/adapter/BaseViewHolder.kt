package com.raxors.photobooth.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder{

    constructor(parent: ViewGroup, layoutId: Int) : super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    constructor(view: View) : super(view)
    init {
        Log.d("CurrentViewHolder", this.javaClass.name)
    }

    open fun bind(model: BaseModel, viewHolder: BaseViewHolder) {}
    open fun bindPayload(model : BaseModel, viewHolder: BaseViewHolder, payloads : MutableList<Any>){}
}