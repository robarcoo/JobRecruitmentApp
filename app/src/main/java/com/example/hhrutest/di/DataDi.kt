package com.example.hhrutest.di
import android.content.Context
import com.example.data.client.httpClientAndroid
import com.example.data.services.VacancyService
import com.example.data.services.VacancyServiceImpl
import io.ktor.client.HttpClient
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext


val dataModule = module {
    single {
        provideHttpClient()
    }
    single<VacancyService> {
        VacancyServiceImpl(client = get(), context = androidContext())
    }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

