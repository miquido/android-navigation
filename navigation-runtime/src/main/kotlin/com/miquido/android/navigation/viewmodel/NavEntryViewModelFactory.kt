package com.miquido.android.navigation.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

interface NavEntryViewModelFactory {
    @Composable
    fun <VM : ViewModel> get(
        modelClass: Class<VM>,
        viewModelStoreOwner: ViewModelStoreOwner
    ): VM
}
