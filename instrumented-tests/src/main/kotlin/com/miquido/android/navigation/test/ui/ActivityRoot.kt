package com.miquido.android.navigation.test.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miquido.android.navigation.handler.NavigationHandler
import com.miquido.android.navigation.test.TestNavigationViewModel
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun ActivityRoot(
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavigationHandler(navController = navController) {
            navEntryViewModel<TestNavigationViewModel>(viewModelStoreOwner = it)
        }

        NavHost(
            navController = navController,
            route = "root",
            startDestination = startDestination,
            builder = builder
        )
    }
}
