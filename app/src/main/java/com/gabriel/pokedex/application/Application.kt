package com.gabriel.pokedex.application

import android.app.Application
import com.gabriel.pokedex.core.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@Application)
            // modules
            modules(appComponent)
        }
    }
}