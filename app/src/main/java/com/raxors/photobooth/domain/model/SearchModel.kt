package com.raxors.photobooth.domain.model

import com.raxors.photobooth.base.adapter.BaseModel
import com.raxors.photobooth.data.model.response.ProfileResponse

data class SearchModel(
    val user: ProfileResponse,
    var isAdded: Boolean = false
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is SearchModel && other.user.id == this.user.id
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        return other is SearchModel && other.user == this.user
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is SearchModel)
            return payloads
        if (this.isAdded != other.isAdded)
            payloads.add(SearchPayload.ADD)
        return payloads
    }

}

enum class SearchPayload {
    ADD
}