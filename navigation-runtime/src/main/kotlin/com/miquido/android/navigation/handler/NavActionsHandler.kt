package com.miquido.android.navigation.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.miquido.android.navigation.NavAction
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
                    is NavAction.To -> navController.navigate(action.direction, action.options)
                    is NavAction.Deeplink -> navController.navigate(action.uri, action.options)
                    is NavAction.Pop -> navController.popBackStack(action.route, action.inclusive, action.saveState)
                    is NavAction.Up -> navController.navigateUp()
                }
            }
        }
}
