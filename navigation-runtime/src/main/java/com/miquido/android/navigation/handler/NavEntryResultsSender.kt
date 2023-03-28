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
import com.ramcosta.composedestinations.spec.Route
import com.ramcosta.composedestinations.utils.navGraph
import com.ramcosta.composedestinations.utils.route
import kotlinx.coroutines.launch
import java.io.Serializable

@Composable
internal fun NavEntryResultsSender(
    viewModel: NavigationViewModel,
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
    val origin = currNavEntry.resultOrigin()
    val recipientOfKey = navEntryRecipientOfKey()
    val recipientOf = prevNavEntry.savedStateHandle.get<Bundle>(recipientOfKey)
    if (recipientOf?.containsKey(origin.route) == true) {
        val type = requireNotNull(recipientOf.serializable<Class<*>>(origin.route)).kotlin
        val canceledKey = navEntryCanceledKey(origin, type)
        if (!prevNavEntry.savedStateHandle.contains(canceledKey)) {
            prevNavEntry.savedStateHandle[canceledKey] = true
        }
    }
}

private suspend fun handlePublishingNavEntryResult(
    viewModel: NavigationViewModel,
    currNavEntry: NavBackStackEntry,
    prevNavEntry: NavBackStackEntry
) = viewModel.navResults
    .flowWithLifecycle(currNavEntry.lifecycle, CREATED)
    .collect { navResult ->
        val origin = currNavEntry.resultOrigin()
        val resultKey = navEntryResultKey(origin, navResult.value::class)
        val canceledKey = navEntryCanceledKey(origin, navResult.value::class)
        prevNavEntry.savedStateHandle[canceledKey] = false
        prevNavEntry.savedStateHandle[resultKey] = navResult.value
    }

private fun NavBackStackEntry.resultOrigin(): Route {
    val navGraph = navGraph()
    val isGraphStartDestination = navGraph.startRoute == route()
    return if (isGraphStartDestination) navGraph else route()
}

@Suppress("DEPRECATION")
private inline fun <reified T : Serializable> Bundle.serializable(
    key: String
): T? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializable(key, T::class.java)
} else {
    getSerializable(key) as? T
}
