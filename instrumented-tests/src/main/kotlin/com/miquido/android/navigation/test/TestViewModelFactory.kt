package com.miquido.android.navigation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class TestViewModelFactory(
    builder: InitializerViewModelFactoryBuilder.() -> Unit = {}
) : ViewModelProvider.Factory {

    private val delegate = viewModelFactory {
        initializer { TestNavigationViewModel(getNavigation(), createSavedStateHandle()) }
        apply(builder)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = delegate.create(modelClass)

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = delegate.create(modelClass, extras)
}
