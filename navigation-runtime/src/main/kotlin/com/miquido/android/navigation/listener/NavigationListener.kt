package com.miquido.android.navigation.listener

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miquido.android.navigation.NavAction
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel

@Composable
fun NavigationListener(
    navController: NavController,
    navigationViewModelProvider: @Composable (NavBackStackEntry) -> AbstractNavigationViewModel,
    onNavAction: (NavAction) -> Unit
) {
    val currentNavEntry by navController.currentBackStackEntryAsState()
    val currentOnNavAction by rememberUpdatedState(onNavAction)
    currentNavEntry?.let { navEntry ->
        val navigationViewModel = navigationViewModelProvider(navEntry)
        LaunchedEffect(navEntry, navController, navigationViewModel) {
            navigationViewModel.navActions.collect { currentOnNavAction(it) }
        }
    }
}
