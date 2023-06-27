package com.domagojdragic.diplomskirad.di

import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository
import com.domagojdragic.diplomskirad.model.repository.ImageRepositoryImpl
import com.domagojdragic.diplomskirad.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(named<ImageRepositoryImpl>()))
    }
}

val repositoryModule = module {
    single<ImageRepository> { ImageRepositoryImpl() }
}
