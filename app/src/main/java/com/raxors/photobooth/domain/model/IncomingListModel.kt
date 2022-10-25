package com.raxors.photobooth.domain.model

import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.response.ProfileResponse

data class IncomingListModel(
    val user: ProfileResponse,
    var isAdded: Boolean = false,
    var isDeclined: Boolean = false
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is IncomingListModel && other.user.id == this.user.id
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        return other is IncomingListModel && other.user == this.user
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is IncomingListModel)
            return payloads
        if (this.isAdded != other.isAdded)
            payloads.add(IncomingListPayload.ADD)
        if (this.isDeclined != other.isDeclined)
            payloads.add(IncomingListPayload.DECLINE)
        return payloads
    }

}

enum class IncomingListPayload {
    ADD, DECLINE
}