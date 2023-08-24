package com.domagojdragic.diplomskirad.di

import com.domagojdragic.diplomskirad.model.interfaces.ImageRepository
import com.domagojdragic.diplomskirad.model.repository.ImageRepositoryImpl
import com.domagojdragic.diplomskirad.networking.OAuth2Client
import com.domagojdragic.diplomskirad.networking.StorageApi
import com.domagojdragic.diplomskirad.networking.StorageApiImpl
import com.domagojdragic.diplomskirad.viewmodel.AnnotationViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

const val MAIN_DISPATCHER = "MAIN_DISPATCHER"
const val BG_DISPATCHER = "BACKGROUND_DISPATCHER"

val viewModelModule = module {

    viewModel { AnnotationViewModel(get(), get(named(BG_DISPATCHER))) }
}

val repositoryModule = module {

    single<ImageRepository> { ImageRepositoryImpl(get()) }
}

val networkModule = module {

    single { OAuth2Client }
}

val serviceModule = module {

    single<StorageApi> { StorageApiImpl(get()) }
}

val concurrencyModule = module {
    single<CoroutineContext>(named(MAIN_DISPATCHER)) { Dispatchers.Main }
    single<CoroutineContext>(named(BG_DISPATCHER)) { Dispatchers.IO }
}
