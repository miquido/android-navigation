package com.miquido.android.navigation.sample.next

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val nextModule = module {
    viewModelOf(::NextViewModel)
}
