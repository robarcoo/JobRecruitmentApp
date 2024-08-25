package com.example.hhrutest.di

import com.example.domain.usecase.GetAllResponseUseCase
import com.example.domain.usecase.GetAllResponseUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllResponseUseCase> {
        GetAllResponseUseCaseImpl(repository = get())
    }
}