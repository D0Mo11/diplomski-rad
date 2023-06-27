package com.domagojdragic.diplomskirad

import android.app.Application
import com.domagojdragic.diplomskirad.di.repositoryModule
import com.domagojdragic.diplomskirad.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiplomskiRadApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DiplomskiRadApp)
            modules(
                viewModelModule,
                repositoryModule,
            )
        }
    }
}
