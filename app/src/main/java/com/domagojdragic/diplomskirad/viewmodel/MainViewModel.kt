package com.domagojdragic.diplomskirad.viewmodel

import androidx.lifecycle.ViewModel
import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository

class MainViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
}
