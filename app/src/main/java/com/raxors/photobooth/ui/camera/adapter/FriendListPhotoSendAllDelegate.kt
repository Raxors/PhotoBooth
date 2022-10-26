package com.raxors.photobooth.ui.camera.adapter

import android.view.ViewGroup
import com.raxors.photobooth.R
import com.raxors.photobooth.base.adapter.AdapterDelegate
import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.base.adapter.BaseViewHolder
import com.raxors.photobooth.databinding.ItemCheckAllBinding

class FriendListPhotoSendAllDelegate(
    val checkAll: () -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        FriendListPhotoSendAllViewHolder(
            parent,
            checkAll = checkAll
        )

    override fun isValidType(model: BaseModel): Boolean = model is CheckAllModel

    inner class FriendListPhotoSendAllViewHolder(
        val parent: ViewGroup,
        val checkAll: () -> Unit = {}
    ) : BaseViewHolder(parent, R.layout.item_check_all) {

        lateinit var binding: ItemCheckAllBinding

        override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
            binding = ItemCheckAllBinding.bind(itemView)
            with(binding) {
                model as CheckAllModel
                if (model.isChecked)
                    tvCheckAll.setBackgroundResource(R.drawable.blue_circle_background)
                else
                    tvCheckAll.setBackgroundResource(R.drawable.background_circle_gray)
                tvCheckAll.setOnClickListener {
                    checkAll()
                }
            }
        }
    }

}

data class CheckAllModel(
    var isChecked: Boolean = false
): BaseModel {

    override fun isContentDiff(other: BaseModel): Boolean {
        return other is CheckAllModel && other.isChecked == this.isChecked
    }
}

