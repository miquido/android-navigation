package com.miquido.android.navigation.koin.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miquido.android.navigation.handler.NavigationHandler
import com.miquido.android.navigation.koin.sample.main.Main
import com.miquido.android.navigation.koin.sample.next.Next
import com.miquido.android.navigation.koin.sample.result.Result

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
            route = "root",
            startDestination = "start"
        ) {
            composable("start") {
                Main()
            }
            composable("next") {
                Next()
            }
            composable("result") {
                Result()
            }
        }
    }
}
