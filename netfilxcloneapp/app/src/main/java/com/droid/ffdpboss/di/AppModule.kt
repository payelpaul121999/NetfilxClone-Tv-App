package com.droid.ffdpboss.di

import com.droid.ffdpboss.gameScreen.GameScreenViewModel
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.dashboard.WinScreenViewModel
import com.droid.ffdpboss.data.DataPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        AuthViewModel(get(), get())
    }
    viewModel {
        GameScreenViewModel(get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        WinScreenViewModel(get(), get())
    }
    single<DataPreferences> { DataPreferences(androidApplication()) }
}