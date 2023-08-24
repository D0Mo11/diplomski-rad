package com.domagojdragic.diplomskirad.model.entity

import com.domagojdragic.diplomskirad.networking.ImageResponse

data class ImageEntity(
    val imageName: String,
    val selfLink: String,
    val contentType: String,
    var isComplete: Boolean,
)

fun ImageResponse.toImageEntity() = ImageEntity(
    imageName = imageName,
    selfLink = selfLink,
    contentType = contentType,
    isComplete = false,
)
