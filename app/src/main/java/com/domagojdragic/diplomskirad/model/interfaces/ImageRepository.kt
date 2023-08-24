package com.domagojdragic.diplomskirad.model.interfaces

import com.domagojdragic.diplomskirad.model.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getStorageImages(): Flow<List<ImageEntity>>
    suspend fun fetchStorageImages()
}
