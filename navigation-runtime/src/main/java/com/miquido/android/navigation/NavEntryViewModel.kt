package com.miquido.android.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

@Composable
inline fun <reified VM : ViewModel> navEntryViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
): VM = navEntryViewModel(VM::class.java, viewModelStoreOwner)

@Composable
fun <VM : ViewModel> navEntryViewModel(
    modelClass: Class<VM>,
    viewModelStoreOwner: ViewModelStoreOwner
): VM {
    return (viewModelStoreOwner as? NavBackStackEntry)?.let { navEntry ->
        val activity = LocalContext.current.activity() ?: throw IllegalStateException(
            "Expected an activity context for creating a NavEntryViewModelFactory for a NavBackStackEntry."
        )
        val factory = HiltViewModelFactory.createInternal(activity, navEntry, null, navEntry.defaultViewModelProviderFactory)

        val extras = MutableCreationExtras(navEntry.defaultViewModelCreationExtras)
        extras[DEFAULT_ARGS_KEY] = bundleOf().apply {
            putString(NAV_ENTRY_ID, navEntry.id)
            extras[DEFAULT_ARGS_KEY]?.let(::putAll)
        }

        viewModel(
            modelClass = modelClass,
            viewModelStoreOwner = viewModelStoreOwner,
            factory = factory,
            extras = extras
        )
    } ?: viewModel(
        modelClass = modelClass,
        viewModelStoreOwner = viewModelStoreOwner
    )
}

private fun Context.activity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
