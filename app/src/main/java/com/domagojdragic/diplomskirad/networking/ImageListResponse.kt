package com.domagojdragic.diplomskirad.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageListResponse(
    @SerialName("items")
    val images: List<ImageResponse>
)
