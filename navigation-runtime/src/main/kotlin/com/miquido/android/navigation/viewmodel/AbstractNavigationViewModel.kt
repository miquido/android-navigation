package com.miquido.android.navigation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.NavAction
import com.miquido.android.navigation.NavEntryInfo
import com.miquido.android.navigation.NavResult
import com.miquido.android.navigation.NavResultCallback
import com.miquido.android.navigation.NavResultLaunch
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.getNavEntryId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

abstract class AbstractNavigationViewModel(
    private val navigation: Navigation,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navEntryId = savedStateHandle.getNavEntryId()

    internal val navActions: Flow<NavAction> = navigation.navActions(navEntryId)
        .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed())

    internal val navResults: Flow<NavResult> = navigation.navResults(navEntryId)

    internal val resultLaunches: Flow<NavResultLaunch> = navigation.resultLaunches(navEntryId)

    internal val resultCallbacks: Flow<Set<NavResultCallback>> = navigation.resultCallbacks(navEntryId)

    internal fun setPreviousNavEntry(prevNavEntryInfo: NavEntryInfo?) {
        navigation.setPreviousNavEntry(prevNavEntryInfo)
    }

    final override fun onCleared() {
        super.onCleared()
        navigation.clear(navEntryId)
    }
}
