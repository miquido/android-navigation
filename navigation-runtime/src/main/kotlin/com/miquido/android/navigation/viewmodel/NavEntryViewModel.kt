package com.miquido.android.navigation.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified VM : ViewModel> navEntryViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
): VM = NavEntryViewModelProvider.factory?.get(VM::class.java, viewModelStoreOwner)
    ?: throw IllegalStateException(
        """
        |Unable to create nav entry view model! Make sure you added dependency to navigation-hilt or navigation-koin artifact.
        """.trimMargin()
    )
