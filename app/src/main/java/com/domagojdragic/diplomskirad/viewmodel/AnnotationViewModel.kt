package com.domagojdragic.diplomskirad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domagojdragic.diplomskirad.model.entity.ImageEntity
import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnnotationViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _imagesState = MutableStateFlow(emptyList<ImageEntity>())
    val imagesState = _imagesState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.fetchStorageImages()
        }
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getStorageImages()
                .collect() { images ->
                    _imagesState.value = images
                }
        }
    }

    fun getImageName(images: List<ImageEntity>): String {
        return images.first { !it.isComplete }.imageName
    }

}
