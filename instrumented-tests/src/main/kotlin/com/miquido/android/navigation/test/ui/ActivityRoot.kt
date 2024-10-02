package com.miquido.android.navigation.test.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miquido.android.navigation.handler.NavigationHandler
import com.miquido.android.navigation.test.TestNavigationViewModel
import com.miquido.android.navigation.viewmodel.navEntryViewModel
import kotlinx.serialization.Serializable

const val ROOT_ROUTE = "root"

@Composable
fun ActivityRoot(
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit
) {
    ActivityRoot { navController ->
        NavHost(
            navController = navController,
            route = ROOT_ROUTE,
            startDestination = startDestination,
            builder = builder,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        )
    }
}

@Serializable
object RootRoute

@Composable
fun ActivityRoot(
    startDestination: Any,
    builder: NavGraphBuilder.() -> Unit
) {
    ActivityRoot { navController ->
        NavHost(
            navController = navController,
            route = RootRoute::class,
            startDestination = startDestination,
            builder = builder,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        )
    }
}

@Composable
private fun ActivityRoot(
    navHost: @Composable (navController: NavHostController) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavigationHandler(navController = navController) {
            navEntryViewModel<TestNavigationViewModel>(viewModelStoreOwner = it)
        }
        navHost(navController)
    }
}
