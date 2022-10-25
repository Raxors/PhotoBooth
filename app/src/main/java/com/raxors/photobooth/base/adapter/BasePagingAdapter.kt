package com.raxors.photobooth.base.adapter

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raxors.photobooth.ui.viewholders.LoaderDelegate
import com.raxors.photobooth.utils.DEFAULT_INT
import java.util.*

abstract class BasePagingAdapter(
    delegates: List<AdapterDelegate>
) : PagingDataAdapter<BaseModel, BaseViewHolder>(BaseDiffUtil()) {
    private val delegateManager = AdapterDelegateManager()

    init {
        delegates.forEach {
            delegateManager.addDelegate(it)
        }
        delegateManager.addDelegate(LoaderDelegate())
    }

    lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        delegateManager.getDelegate(viewType).onCreateViewHolder(parent)


    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        Log.d("onBindViewHolder", holder.javaClass.name.toString())
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            getItem(position)?.let { holder.bindPayload(it, holder, payloads[0] as MutableList<Any>) }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("onBindViewHolder", holder.javaClass.name.toString())
        getItem(position)?.let { holder.bind(it, holder) }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.let {
        delegateManager.getItemViewType(it)
    } ?: DEFAULT_INT

//    override fun submitList(list: MutableList<BaseModel>?) {
//        synchronized(currentList) {
//            super.submitList(list) {
//                Handler(Looper.getMainLooper()).post { recyclerView.invalidateItemDecorations() }
//            }
//        }
//    }

//    fun submitItem(item: BaseModel) {
//        synchronized(currentList) {
//            super.submitList(mutableListOf<BaseModel>().apply { add(item) })
//        }
//    }

//    override fun submitList(list: MutableList<BaseModel>?, commitCallback: Runnable?) {
//        synchronized(currentList) {
//            super.submitList(list, commitCallback)
//        }
//    }

    fun isEmpty() = this.itemCount == 0
}