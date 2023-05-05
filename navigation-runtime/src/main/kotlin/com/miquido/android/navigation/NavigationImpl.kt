package com.miquido.android.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@Suppress("TooManyFunctions")
internal class NavigationImpl : Navigation() {

    private val _previousNavEntry = MutableStateFlow<NavEntryInfo?>(null)
    private val navActions = mutableMapOf<NavEntryId, Channel<NavAction>>()
    private val navResults = mutableMapOf<NavEntryId, Channel<NavResult>>()
    private val navResultLaunches = mutableMapOf<NavEntryId, Channel<NavResultLaunch>>()
    private val navResultCallbacks = mutableMapOf<NavEntryId, MutableStateFlow<Set<NavResultCallback>>>()

    override val previousNavEntry: NavEntryInfo?
        get() = _previousNavEntry.value

    override fun navActions(navEntryId: NavEntryId): Flow<NavAction> =
        navActions.retrieve(navEntryId).receiveAsFlow()

    override fun navResults(navEntryId: NavEntryId): Flow<NavResult> =
        navResults.retrieve(navEntryId).receiveAsFlow()

    override fun resultLaunches(navEntryId: NavEntryId): Flow<NavResultLaunch> =
        navResultLaunches.retrieve(navEntryId).receiveAsFlow()

    override fun resultCallbacks(navEntryId: NavEntryId): StateFlow<Set<NavResultCallback>> =
        navResultCallbacks.retrieve(navEntryId).asStateFlow()

    override suspend fun dispatchAction(navEntryId: NavEntryId, navAction: NavAction) {
        navActions.retrieve(navEntryId).send(navAction)
    }

    override suspend fun dispatchResult(navEntryId: NavEntryId, navResult: NavResult) {
        navResults.retrieve(navEntryId).send(navResult)
    }

    override suspend fun dispatchResultLaunch(navEntryId: NavEntryId, launch: NavResultLaunch) {
        navResultLaunches.retrieve(navEntryId).send(launch)
    }

    override fun addResultCallback(navEntryId: NavEntryId, registration: NavResultCallback) {
        navResultCallbacks.retrieve(navEntryId).run {
            tryEmit(value + registration)
        }
    }

    override fun removeResultCallback(navEntryId: NavEntryId, registration: NavResultCallback) {
        navResultCallbacks[navEntryId]?.run {
            tryEmit(value - registration)
        }
    }

    override fun setPreviousNavEntry(prevNavEntryInfo: NavEntryInfo?) {
        _previousNavEntry.update { prevNavEntryInfo }
    }

    override fun clear(navEntryId: NavEntryId) {
        navActions.remove(navEntryId)?.close()
        navResults.remove(navEntryId)?.close()
        navResultLaunches.remove(navEntryId)?.close()
        navResultCallbacks.remove(navEntryId)
    }
}

private fun <T> MutableMap<NavEntryId, Channel<T>>.retrieve(key: NavEntryId): Channel<T> =
    getOrPut(key) { Channel(Channel.BUFFERED) }

private fun <T> MutableMap<NavEntryId, MutableStateFlow<Set<T>>>.retrieve(key: NavEntryId): MutableStateFlow<Set<T>> =
    getOrPut(key) { MutableStateFlow(emptySet()) }
