package com.raxors.photobooth.base.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raxors.photobooth.ui.viewholders.LoaderDelegate
import com.raxors.photobooth.utils.DEFAULT_INT
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

abstract class BaseAdapter(
    delegates: List<AdapterDelegate>,
    clickToAction: () -> Unit = {}
) : RecyclerView.Adapter<BaseViewHolder>(){

    protected val postModels = CopyOnWriteArrayList<BaseModel>()
    private val delegateManager = AdapterDelegateManager()

    init {
        delegates.forEach {
            delegateManager.addDelegate(it)
        }
        delegateManager.addDelegate(LoaderDelegate())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
            = delegateManager.getDelegate(viewType).onCreateViewHolder(parent)

    override fun getItemCount(): Int
            = postModels.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("onBindViewHolder", holder.javaClass.name.toString())
        holder.bind(postModels[position], holder)
    }

    override fun getItemViewType(position: Int): Int
            = delegateManager.getItemViewType(postModels[position])

    fun getItemByPosition(position: Int) = if(position != DEFAULT_INT) postModels[position] else null

    fun getItems() = postModels

    fun isContains(model: BaseModel): Boolean {
        var contains = false
        postModels.forEach {
            if(it.javaClass == model.javaClass) {
                contains = true
            }
        }
        return contains
    }

    fun getZeroItem() = if(postModels.size > 0) postModels[0] else null

    fun add(input: BaseModel) = with(postModels){
        add(input)
        notifyItemInserted(size)
    }

    fun addWithFooter(input: BaseModel) = with(postModels){
        add(size - 1, input)
        notifyItemInserted(size)
    }

    fun addHead(input: BaseModel) = with(postModels){
        add(0, input)
        notifyItemInserted(0)
    }

    fun add(position: Int, input: BaseModel?) = with(postModels){
        input?.let {
            add(position, input)
            notifyItemInserted(position)
        }
    }

    fun add(input: List<BaseModel>?) = with(postModels){
        input ?: return@with
        val currentSize = size
        addAll(input)
        notifyItemRangeInserted(currentSize, input.size)
    }

    fun add(position: Int, input: List<BaseModel>?) = with(postModels){
        input ?: return@with
        addAll(position + 1, input)
        notifyItemRangeInserted(position + 1, input.size)
    }

    fun replace(input: List<BaseModel>?) = with(postModels) {
        input ?: return@with
        clear()
        addAll(input)
        notifyDataSetChanged()
    }

    fun replace(input: BaseModel?) = with(postModels) {
        input ?: return@with
        clear()
        add(input)
        notifyDataSetChanged()
    }

    fun remove(input: BaseModel?) = with(postModels){
        input ?: return@with
        val index = indexOf(input)
        removeAt(index)
        notifyItemRemoved(index)
    }

    fun remove(position: Int, withNotifyAll: Boolean = false) = with(postModels) {
        removeAt(position)
        if(!withNotifyAll) notifyItemRemoved(position)
        else notifyDataSetChanged()
    }

    fun remove(input: MutableList<BaseModel>?, position: Int) = with(postModels){
        input ?: return@with
        removeAll(input)
        notifyItemRangeRemoved(position + 1, input.size)
    }

    fun change(input: BaseModel) = with(postModels){
        if (contains(input)){
            val index = indexOf(input)
            removeAt(index)
            add(index, input)
            notifyItemChanged(index)
        }
    }

    fun change(input: BaseModel, update: BaseModel) = with(postModels){
        if (contains(input)){
            val index = indexOf(input)
            removeAt(index)
            add(index, update)
            notifyItemChanged(index)
        }
    }

    fun changeAndMoveToStart(input: BaseModel, update: BaseModel, done:() -> Unit = {}) = with(postModels){
        if (contains(input)){
            val index = indexOf(input)
            removeAt(index)
            add(0, update)
            notifyItemMoved(index, 0)
            notifyItemChanged(0)
            done()
        }
    }

    inline fun <reified T: BaseModel> findAndUpdate(
        update: (model: BaseModel) -> Unit
    ): Boolean {
        return accessBaseModels.find { it is T }?.let { model ->
            val index = accessBaseModels.indexOf(model)
            update(model as T)
            notifyItemChanged(index)
            true
        } ?: false
    }

    inline fun <reified T : BaseModel> findAndChange(
        condition: (model: T) -> Boolean,
        update: (model: T) -> Unit
    ): Boolean {
        return accessBaseModels.find { it is T && condition(it) }?.let { model ->
            val index = accessBaseModels.indexOf(model)
            update(model as T)
            notifyItemChanged(index)
            true
        } ?: false
    }

    inline fun <reified T : BaseModel> findAndChange(
        position: Int,
        update: (model: T) -> Unit
    ): Boolean {
        return if(position > -1 && position <= accessBaseModels.size - 1)
            accessBaseModels[position].let { model ->
                val index = accessBaseModels.indexOf(model)
                update(model as T)
                notifyItemChanged(index)
                true
            } else false
    }

    fun findAndChange(position: Int, update: (model: BaseModel) -> Unit) {
        accessBaseModels[position].let {model ->
            update(model)
            notifyItemChanged(position)
        }
    }

    inline fun <reified T : BaseModel> findAndChangeWithoutNotify(
        position: Int,
        update: (model: T) -> Unit
    ): Boolean {
        return accessBaseModels[position].let { model ->
            update(model as T)
            true
        } ?: false
    }

    inline fun <reified T : BaseModel> findAndChangeForAll(
        condition: (model: T) -> Boolean,
        update: (model: T) -> Unit
    ) { accessBaseModels.forEach { model ->
        if(model is T && condition(model)) {
            update(model)
        }
    }
        notifyDataSetChanged()
    }

    inline fun <reified T : BaseModel> findAndChangeWithoutNotifyForAll(
        condition: (model: T) -> Boolean,
        update: (model: T) -> Unit
    ) { accessBaseModels.forEach { model ->
        if(model is T && condition(model)) {
            update(model)
        }
    }
    }

    inline fun <reified T : BaseModel> findAndChangeWithoutNotify(
        condition: (model: T) -> Boolean,
        update: (model: T) -> Unit
    ): Boolean {
        return accessBaseModels.find { it is T && condition(it) }?.let { model ->
            update(model as T)
            true
        } ?: false
    }

    inline fun <reified T : BaseModel> findAndReplace(
        condition: (oldModel: T) -> Boolean,
        replaceBy: (oldModel: T) -> T
    ): Boolean {
        return accessBaseModels.find { it is T && condition(it) }?.let { oldModel ->
            val index = accessBaseModels.indexOf(oldModel)
            accessBaseModels[index] = replaceBy(oldModel as T)
            notifyItemChanged(index)
            true
        } ?: false
    }

    inline fun <reified T : BaseModel> findAndRemove(condition : (model: T) -> Boolean): Boolean {
        return with(accessBaseModels) {
            find { it is T && condition(it) }?.let { model ->
                val index = indexOf(model)
                removeAt(index)
                notifyItemRemoved(index)
                true
            } ?: false
        }
    }

    inline fun <reified T : BaseModel> findAndRemove(): Boolean {
        return with(accessBaseModels) {
            find { it is T }?.let { model ->
                val index = indexOf(model)
                removeAt(index)
                notifyItemRemoved(index)
                true
            } ?: false
        }
    }

    fun replaceModel(position: Int, input: BaseModel) {
        postModels.removeAt(position)
        postModels.add(position, input)
        notifyItemChanged(position)
    }

    fun clear() = with(postModels){
        val size = size
        clear()
        notifyItemRangeRemoved(0, size)
    }

    fun clearAfter(index: Int) = with(postModels){
        val size = size
        subList(index, size).clear()
        notifyItemRangeRemoved(index, size)
    }

    fun onItemMoved(fromPosition: Int, toPosition: Int) {
        if(fromPosition != DEFAULT_INT && toPosition != DEFAULT_INT) {
            Collections.swap(postModels, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    fun isEmpty() = postModels.isEmpty()

    @PublishedApi
    internal val accessBaseModels: MutableList<BaseModel>
        get() = postModels

}