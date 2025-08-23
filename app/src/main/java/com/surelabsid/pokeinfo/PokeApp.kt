package com.surelabsid.pokeinfo

import android.app.Application
import com.surelabsid.pokeinfo.di.networkModules
import com.surelabsid.pokeinfo.di.useCaseModules
import com.surelabsid.pokeinfo.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class PokeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PokeApp)
            modules(
                networkModules,
                viewModelModules,
                useCaseModules
            )
        }
    }

}