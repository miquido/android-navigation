package com.miquido.android.navigation.listener

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miquido.android.navigation.NavAction
import com.miquido.android.navigation.handler.NavigationViewModel
import com.miquido.android.navigation.navEntryViewModel

@Composable
fun NavigationListener(
    navController: NavController,
    onNavAction: (NavAction) -> Unit
) {
    val currentNavEntry by navController.currentBackStackEntryAsState()
    val currentOnNavAction by rememberUpdatedState(onNavAction)
    currentNavEntry?.let { navEntry ->
        val navigationViewModel = navEntryViewModel<NavigationViewModel>(navEntry)
        LaunchedEffect(navEntry, navController, navigationViewModel) {
            navigationViewModel.navActions.collect { currentOnNavAction(it) }
        }
    }
}
