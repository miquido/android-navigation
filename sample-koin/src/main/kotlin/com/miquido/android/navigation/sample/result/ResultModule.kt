package com.miquido.android.navigation.sample.result

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val resultModule = module {
    viewModelOf(::ResultViewModel)
}
