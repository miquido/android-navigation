@file:JvmName("NavEntryResultsRecipientComposable")

package com.miquido.android.navigation.handler

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import com.miquido.android.navigation.NavResultCallback
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest

@Composable
internal fun NavEntryResultsRecipient(
    viewModel: AbstractNavigationViewModel,
    navEntry: NavBackStackEntry
) = LaunchedEffect(viewModel, navEntry) {
    viewModel.resultCallbacks
        .flatMapLatest { it.asFlow() }
        .filterIsInstance<NavResultCallback.NavEntry>()
        .flowWithLifecycle(navEntry.lifecycle, CREATED)
        .collect { registration ->
            attachNavEntryRecipientOfInformation(navEntry, registration)
            checkForAvailableNavEntryResults(navEntry, registration)
        }
}

private fun attachNavEntryRecipientOfInformation(
    navEntry: NavBackStackEntry,
    registration: NavResultCallback.NavEntry
) = with(navEntry.savedStateHandle) {
    val recipientOfKey = navEntryRecipientOfKey()
    val recipientOfBundle = get<Bundle>(recipientOfKey) ?: bundleOf()
    set(
        key = recipientOfKey,
        value = recipientOfBundle.apply {
            putSerializable(registration.originRoute, registration.type.java)
        }
    )
}

private suspend fun checkForAvailableNavEntryResults(
    navEntry: NavBackStackEntry,
    registration: NavResultCallback.NavEntry
) = navEntry.repeatOnLifecycle(STARTED) {
    val resultKey = navEntryResultKey(registration.originRoute, registration.type)
    val canceledKey = navEntryCanceledKey(registration.originRoute, registration.type)

    with(navEntry.savedStateHandle) {
        if (remove<Boolean>(canceledKey) == true) {
            registration.onResult(null)
        } else {
            remove<Any>(resultKey)?.let { result ->
                registration.onResult(result)
            }
        }
    }
}
