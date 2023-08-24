package com.domagojdragic.diplomskirad.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domagojdragic.diplomskirad.model.entity.ImageEntity
import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AnnotationViewModel(
    private val imageRepository: ImageRepository,
    bgDispatcher: CoroutineContext,
) : ViewModel() {

    private val _imagesState = MutableStateFlow(emptyList<ImageEntity>())
    val imagesState = _imagesState.asStateFlow()

    init {
        viewModelScope.launch(bgDispatcher) {
            imageRepository.fetchStorageImages()
        }
        viewModelScope.launch(bgDispatcher) {
            imageRepository.getStorageImages()
                .collect() { images ->
                    _imagesState.value = images
                }
        }
    }

    fun getImageName(images: List<ImageEntity>): String? {
        return images.firstOrNull { !it.isComplete }?.imageName
    }

    fun getCurrentImage(images: List<ImageEntity>): ImageEntity {
        return images.first { !it.isComplete }
    }

    fun updateIsComplete(image: ImageEntity) {
        _imagesState.value.find { it.imageName == image.imageName }?.apply { isComplete = true }
    }
}
