package com.miquido.android.navigation.di

import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.handler.KoinNavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val navigationModule = module {
    single { Navigation.Default() }
    viewModelOf(::KoinNavigationViewModel)
}
