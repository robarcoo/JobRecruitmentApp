package com.example.hhrutest.di
import com.example.data.client.httpClientAndroid
import com.example.data.repository.ResponseRepository
import com.example.data.services.ResponseService
import com.example.data.services.ResponseServiceImpl
import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext


val dataModule = module {
    single {
        provideHttpClient()
    }
    single<ResponseService> {
        ResponseServiceImpl(client = get())
    }

    single<ResponseRepository> {
        ResponseRepository(remoteDataSource = get(), context = androidContext())
    }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

