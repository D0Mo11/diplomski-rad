package com.domagojdragic.diplomskirad.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("name")
    val imageName: String,
    @SerialName("selfLink")
    val selfLink: String,
    @SerialName("contentType")
    val contentType: String,
)
