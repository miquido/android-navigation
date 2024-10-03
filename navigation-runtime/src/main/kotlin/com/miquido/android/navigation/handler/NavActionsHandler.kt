package com.miquido.android.navigation.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.miquido.android.navigation.NavAction.Deeplink
import com.miquido.android.navigation.NavAction.NavigateTo
import com.miquido.android.navigation.NavAction.NavigateUp
import com.miquido.android.navigation.NavAction.PopBackTo
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
internal fun NavActionsHandler(
    viewModel: AbstractNavigationViewModel,
    navController: NavController,
    navEntry: NavBackStackEntry
) = LaunchedEffect(viewModel, navController, navEntry) {
    viewModel.navActions
        .flowWithLifecycle(navEntry.lifecycle, RESUMED)
        .collect { action ->
            withContext(Dispatchers.Main) {
                when (action) {
                    is NavigateTo -> navController.navigateTo(action)
                    is Deeplink -> navController.navigate(action.uri, action.options)
                    is PopBackTo -> navController.popBackTo(action)
                    is NavigateUp -> navController.navigateUp()
                }
            }
        }
}

private fun NavController.navigateTo(action: NavigateTo) = when {
    action.routeString != null -> navigate(action.routeString, action.options)
    action.routeAny != null -> navigate(action.routeAny, action.options)
    else -> throw IllegalArgumentException("Invalid route provided!")
}

private fun NavController.popBackTo(action: PopBackTo) = when {
    action.routeString != null -> popBackStack(action.routeString, action.inclusive, action.saveState)
    action.routeAny != null -> popBackStack(action.routeAny, action.inclusive, action.saveState)
    else -> throw IllegalArgumentException("Invalid route provided!")
}
