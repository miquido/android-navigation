package com.miquido.android.navigation.test

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.miquido.android.navigation.NAV_ENTRY_ID
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.viewmodel.NavEntryViewModelFactory

class TestNavEntryViewModelFactory(
    private val navigation: Navigation,
    private val factory: ViewModelProvider.Factory
) : NavEntryViewModelFactory {

    @Composable
    override fun <VM : ViewModel> get(
        modelClass: Class<VM>,
        viewModelStoreOwner: ViewModelStoreOwner
    ): VM {
        require(viewModelStoreOwner is NavBackStackEntry) {
            "Unexpected view model store owner ${viewModelStoreOwner.javaClass.name}"
        }
        val navEntry = viewModelStoreOwner

        val extras = MutableCreationExtras(navEntry.defaultViewModelCreationExtras)
        extras[DEFAULT_ARGS_KEY] = bundleOf().apply {
            putString(NAV_ENTRY_ID, navEntry.id)
            extras[DEFAULT_ARGS_KEY]?.let(::putAll)
        }
        extras[NAVIGATION_KEY] = navigation

        return viewModel(
            modelClass = modelClass,
            viewModelStoreOwner = viewModelStoreOwner,
            factory = factory,
            extras = extras
        )
    }
}
