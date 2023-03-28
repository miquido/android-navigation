package com.miquido.android.navigation.handler

import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import kotlin.reflect.KClass

@Composable
fun NavigationHandler(
    navController: NavController,
    navigationViewModelProvider: @Composable (NavBackStackEntry) -> AbstractNavigationViewModel
) {
    val currentNavEntry by navController.currentBackStackEntryAsState()
    currentNavEntry?.let { navEntry ->
        val navigationViewModel = navigationViewModelProvider(navEntry)
        NavActionsHandler(
            viewModel = navigationViewModel,
            navController = navController,
            navEntry = navEntry
        )
        NavEntryResultsSender(
            viewModel = navigationViewModel,
            navController = navController,
            currNavEntry = navEntry
        )
        NavEntryResultsRecipient(
            viewModel = navigationViewModel,
            navEntry = navEntry
        )
        ActivityResultsRecipient(
            viewModel = navigationViewModel,
            navEntry = navEntry
        )
    }
}

internal fun activityResultKey(
    id: String,
    type: KClass<out ActivityResultContract<*, *>>
) = "activity-result@$id@${type.qualifiedName}"

@Suppress("FunctionOnlyReturningConstant")
internal fun navEntryRecipientOfKey() = "nav-entry@recipient-of"

internal fun <R : Any> navEntryResultKey(
    originRoute: String,
    resultType: KClass<out R>
) = "nav-entry@$originRoute@${resultType.qualifiedName}@result"

internal fun <R : Any> navEntryCanceledKey(
    originRoute: String,
    resultType: KClass<out R>
) = "nav-entry@$originRoute@${resultType.qualifiedName}@canceled"
