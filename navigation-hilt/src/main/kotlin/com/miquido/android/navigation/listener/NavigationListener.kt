@file:JvmName("NavigationListenerHilt")

package com.miquido.android.navigation.listener

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.miquido.android.navigation.NavAction
import com.miquido.android.navigation.handler.HiltNavigationViewModel
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun NavigationListener(
    navController: NavController,
    onNavAction: (NavAction) -> Unit
) = NavigationListener(
    navController = navController,
    navigationViewModelProvider = { navEntry ->
        navEntryViewModel<HiltNavigationViewModel>(navEntry)
    },
    onNavAction = onNavAction
)
