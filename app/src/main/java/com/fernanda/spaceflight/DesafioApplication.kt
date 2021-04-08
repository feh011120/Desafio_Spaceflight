package com.fernanda.spaceflight

import android.app.Application
import android.content.Context
import com.fernanda.spaceflight.di.applicationModule
import com.fernanda.spaceflight.di.repositoryModule
import com.fernanda.spaceflight.di.viewModelModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DesafioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instace = applicationContext
        setupKoin()
        setupHawk()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@DesafioApplication)
            androidFileProperties()
            modules(applicationModule, viewModelModule, repositoryModule)
        }
    }


    private fun setupHawk() = Hawk.init(this).build()

    companion object {
        lateinit var instace: Context
    }
}
