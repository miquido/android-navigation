package com.miquido.android.navigation

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.NavBackStackEntry
import com.miquido.android.navigation.viewmodel.NavEntryViewModelFactory
import org.koin.androidx.compose.defaultExtras
import org.koin.androidx.viewmodel.resolveViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

internal class KoinNavEntryViewModelFactory : NavEntryViewModelFactory {

    @Composable
    @OptIn(KoinInternalApi::class)
    override fun <VM : ViewModel> get(
        modelClass: Class<VM>,
        viewModelStoreOwner: ViewModelStoreOwner
    ): VM {
        val rootScope = GlobalContext.get().scopeRegistry.rootScope
        return (viewModelStoreOwner as? NavBackStackEntry)?.let { navEntry ->
            val extras = MutableCreationExtras(defaultExtras(navEntry))
            extras[DEFAULT_ARGS_KEY] = bundleOf().apply {
                putString(NAV_ENTRY_ID, navEntry.id)
                extras[DEFAULT_ARGS_KEY]?.let(::putAll)
            }
            resolveViewModel(
                vmClass = modelClass.kotlin,
                viewModelStore = viewModelStoreOwner.viewModelStore,
                scope = rootScope,
                extras = extras,
                parameters = { parametersOf(Navigator.Default(rootScope.get(), NavEntryId(navEntry.id))) }
            )
        } ?: resolveViewModel(
            vmClass = modelClass.kotlin,
            viewModelStore = viewModelStoreOwner.viewModelStore,
            scope = rootScope,
            extras = defaultExtras(viewModelStoreOwner)
        )
    }
}
