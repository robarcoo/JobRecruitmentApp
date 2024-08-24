package com.example.hhrutest.di
import com.example.data.client.httpClientAndroid
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}