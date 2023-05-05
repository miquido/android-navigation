package com.miquido.android.navigation.handler

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.miquido.android.navigation.NavResultCallback
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@Composable
internal fun ActivityResultsRecipient(
    viewModel: AbstractNavigationViewModel,
    navEntry: NavBackStackEntry
) {
    val activityResultRegistry = checkNotNull(LocalActivityResultRegistryOwner.current) {
        "No ActivityResultRegistryOwner was provided via LocalActivityResultRegistryOwner"
    }.activityResultRegistry

    val launchers = remember(navEntry) {
        mutableMapOf<String, ActivityResultLauncher<Any>>()
    }

    LaunchedEffect(viewModel, navEntry) {
        launch {
            registerActivityResultContracts(viewModel, navEntry, launchers, activityResultRegistry)
        }
        launch {
            handleActivityResultLaunches(viewModel, navEntry, launchers)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun registerActivityResultContracts(
    viewModel: AbstractNavigationViewModel,
    navEntry: NavBackStackEntry,
    launchers: MutableMap<String, ActivityResultLauncher<Any>>,
    activityResultRegistry: ActivityResultRegistry
) = viewModel.resultCallbacks
    .flatMapLatest { it.asFlow() }
    .filterIsInstance<NavResultCallback.Activity>()
    .flowWithLifecycle(navEntry.lifecycle, CREATED)
    .collect { registration ->
        val launcherKey = activityResultKey(navEntry.id, registration.contract::class)
        launchers[launcherKey] = activityResultRegistry.register(launcherKey, registration.contract, registration.onResult)
    }

private suspend fun handleActivityResultLaunches(
    viewModel: AbstractNavigationViewModel,
    navEntry: NavBackStackEntry,
    launchers: Map<String, ActivityResultLauncher<Any>>
) = viewModel.resultLaunches
    .flowWithLifecycle(navEntry.lifecycle, RESUMED)
    .collect { launch ->
        val launcherKey = activityResultKey(navEntry.id, launch.type)
        launchers.getValue(launcherKey).launch(launch.input)
    }
