@file:JvmName("NavigationHandlerHilt")

package com.miquido.android.navigation.handler

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun NavigationHandler(
    navController: NavController
) = NavigationHandler(
    navController = navController,
    navigationViewModelProvider = { navEntry ->
        navEntryViewModel<HiltNavigationViewModel>(navEntry)
    }
)
