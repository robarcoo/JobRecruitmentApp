package com.example.hhrutest

import android.app.Application
import androidx.work.WorkManager
import com.example.hhrutest.di.dataModule
import com.example.hhrutest.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, domainModule))
        }
    }

}