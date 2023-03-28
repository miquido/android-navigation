package com.miquido.android.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface Navigation {
    fun navActions(navEntryId: NavEntryId): Flow<NavAction>
    fun navResults(navEntryId: NavEntryId): Flow<NavResult>
    fun resultLaunches(navEntryId: NavEntryId): Flow<NavResultLaunch>
    fun resultCallbacks(navEntryId: NavEntryId): StateFlow<Set<NavResultCallback>>

    suspend fun dispatchAction(navEntryId: NavEntryId, navAction: NavAction)

    suspend fun dispatchResult(navEntryId: NavEntryId, navResult: NavResult)
    suspend fun dispatchResultLaunch(navEntryId: NavEntryId, launch: NavResultLaunch)

    fun addResultCallback(navEntryId: NavEntryId, registration: NavResultCallback)
    fun removeResultCallback(navEntryId: NavEntryId, registration: NavResultCallback)

    fun clear(navEntryId: NavEntryId)
}
