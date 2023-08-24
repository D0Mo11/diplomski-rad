package com.domagojdragic.diplomskirad.model.repository

import com.domagojdragic.diplomskirad.model.entity.ImageEntity
import com.domagojdragic.diplomskirad.model.entity.toImageEntity
import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository
import com.domagojdragic.diplomskirad.networking.StorageApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

const val CONTENT_TYPE_IMAGE = "image/jpeg"

class ImageRepositoryImpl(private val storageApi: StorageApi) : ImageRepository {

    private val storageImages = MutableSharedFlow<List<ImageEntity>>()

    override fun getStorageImages(): Flow<List<ImageEntity>> {
        return storageImages
    }

    override suspend fun fetchStorageImages() =
        storageImages.emit(
            storageApi.fetchFireImages().images
                .filter { imageResponse ->
                    imageResponse.contentType == CONTENT_TYPE_IMAGE
                }
                .shuffled()
                .subList(0, 10)
                .map { filteredImageResponse ->
                    filteredImageResponse.toImageEntity()
                }
        )
}
