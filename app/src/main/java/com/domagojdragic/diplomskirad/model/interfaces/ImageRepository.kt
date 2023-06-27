package com.domagojdragic.diplomskirad.model.interfaces

import com.domagojdragic.diplomskirad.model.entity.ImageEntity

interface ImageRepository {

    fun getImages(): List<ImageEntity>
}
