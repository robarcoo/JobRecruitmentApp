package com.example.hhrutest.di

import com.example.hhrutest.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<DashboardViewModel>{
        DashboardViewModel(
            getAllResponseUseCase = get())
    }

}