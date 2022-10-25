package com.raxors.photobooth.domain.model

import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.response.ProfileResponse

data class OutgoingListModel(
    val user: ProfileResponse,
    var isDeclined: Boolean = false
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is OutgoingListModel && other.user.id == this.user.id
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        return other is OutgoingListModel && other.user == this.user
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is OutgoingListModel)
            return payloads
        if (this.isDeclined != other.isDeclined)
            payloads.add(OutgoingListPayload.DECLINE)
        return payloads
    }

}

enum class OutgoingListPayload {
    DECLINE
}