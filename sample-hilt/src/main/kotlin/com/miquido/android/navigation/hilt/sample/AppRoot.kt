package com.miquido.android.navigation.hilt.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miquido.android.navigation.handler.NavigationHandler
import com.miquido.android.navigation.hilt.sample.main.Main
import com.miquido.android.navigation.hilt.sample.next.Next
import com.miquido.android.navigation.hilt.sample.result.Result
import kotlinx.serialization.Serializable

@Serializable
data object RootRoute

@Serializable
data object StartRoute

@Serializable
data object NextRoute

@Serializable
data object ResultRoute

@Composable
fun AppRoot() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()

        NavigationHandler(
            navController = navController
        )

        NavHost(
            navController = navController,
            route = RootRoute::class,
            startDestination = StartRoute
        ) {
            composable<StartRoute> {
                Main()
            }
            composable<NextRoute> {
                Next()
            }
            composable<ResultRoute> {
                Result()
            }
        }
    }
}
