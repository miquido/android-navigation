@file:JvmName("NavigationListenerKoin")

package com.miquido.android.navigation.listener

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.miquido.android.navigation.NavAction
import com.miquido.android.navigation.handler.KoinNavigationViewModel
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun NavigationListener(
    navController: NavController,
    onNavAction: (NavAction) -> Unit
) = NavigationListener(
    navController = navController,
    navigationViewModelProvider = { navEntry ->
        navEntryViewModel<KoinNavigationViewModel>(navEntry)
    },
    onNavAction = onNavAction
)
