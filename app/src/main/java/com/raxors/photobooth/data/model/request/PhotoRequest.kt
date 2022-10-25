package com.raxors.photobooth.data.model.request

data class PhotoRequest(
    val file: String,
    val userIds: List<String>
)
