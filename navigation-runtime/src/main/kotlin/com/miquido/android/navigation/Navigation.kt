package com.miquido.android.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Suppress("TooManyFunctions")
abstract class Navigation {
    internal abstract val previousNavEntry: NavEntryInfo?

    internal abstract fun navActions(navEntryId: NavEntryId): Flow<NavAction>
    internal abstract fun navResults(navEntryId: NavEntryId): Flow<NavResult>
    internal abstract fun resultLaunches(navEntryId: NavEntryId): Flow<NavResultLaunch>
    internal abstract fun resultCallbacks(navEntryId: NavEntryId): StateFlow<Set<NavResultCallback>>

    internal abstract suspend fun dispatchAction(navEntryId: NavEntryId, navAction: NavAction)

    internal abstract suspend fun dispatchResult(navEntryId: NavEntryId, navResult: NavResult)
    internal abstract suspend fun dispatchResultLaunch(navEntryId: NavEntryId, launch: NavResultLaunch)

    internal abstract fun addResultCallback(navEntryId: NavEntryId, registration: NavResultCallback)
    internal abstract fun removeResultCallback(navEntryId: NavEntryId, registration: NavResultCallback)

    internal abstract fun setPreviousNavEntry(prevNavEntryInfo: NavEntryInfo?)

    internal abstract fun clear(navEntryId: NavEntryId)

    companion object Default {
        operator fun invoke(): Navigation = NavigationImpl()
    }
}
