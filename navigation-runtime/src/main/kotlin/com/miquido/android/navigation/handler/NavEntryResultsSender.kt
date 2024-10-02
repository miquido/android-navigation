@file:JvmName("NavEntryResultsSenderComposable")

package com.miquido.android.navigation.handler

import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import kotlinx.coroutines.launch
import java.io.Serializable

@Composable
internal fun NavEntryResultsSender(
    viewModel: AbstractNavigationViewModel,
    navController: NavController,
    currNavEntry: NavBackStackEntry
) {
    val prevNavEntry = remember(navController, currNavEntry) {
        navController.previousBackStackEntry
    } ?: return

    LaunchedEffect(viewModel, navController, currNavEntry, prevNavEntry) {
        launch {
            markCancelledIfPreviousNavEntryIsRecipient(currNavEntry, prevNavEntry)
        }
        launch {
            handlePublishingNavEntryResult(viewModel, currNavEntry, prevNavEntry)
        }
    }
}

private suspend fun markCancelledIfPreviousNavEntryIsRecipient(
    currNavEntry: NavBackStackEntry,
    prevNavEntry: NavBackStackEntry
) = currNavEntry.lifecycle.repeatOnLifecycle(CREATED) {
    val originRoute = currNavEntry.resultOriginRoute()
    val recipientOfKey = navEntryRecipientOfKey()
    val recipientOf = prevNavEntry.savedStateHandle.get<Bundle>(recipientOfKey)
    if (recipientOf?.containsKey(originRoute) == true) {
        val type = requireNotNull(recipientOf.serializable<Class<*>>(originRoute)).kotlin
        val canceledKey = navEntryCanceledKey(originRoute, type)
        if (!prevNavEntry.savedStateHandle.contains(canceledKey)) {
            prevNavEntry.savedStateHandle[canceledKey] = true
        }
    }
}

private suspend fun handlePublishingNavEntryResult(
    viewModel: AbstractNavigationViewModel,
    currNavEntry: NavBackStackEntry,
    prevNavEntry: NavBackStackEntry
) = viewModel.navResults
    .flowWithLifecycle(currNavEntry.lifecycle, CREATED)
    .collect { navResult ->
        val originRoute = currNavEntry.resultOriginRoute()
        val resultKey = navEntryResultKey(originRoute, navResult.value::class)
        val canceledKey = navEntryCanceledKey(originRoute, navResult.value::class)
        prevNavEntry.savedStateHandle[canceledKey] = false
        prevNavEntry.savedStateHandle[resultKey] = navResult.value
    }

private fun NavBackStackEntry.resultOriginRoute(): String {
    val navGraph = requireNotNull(destination.parent)
    val isGraphStartDestination = navGraph.startDestinationRoute == destination.route
    return requireNotNull(if (isGraphStartDestination) navGraph.route else destination.route)
}

@Suppress("DEPRECATION")
private inline fun <reified T : Serializable> Bundle.serializable(
    key: String
): T? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializable(key, T::class.java)
} else {
    getSerializable(key) as? T
}
