package com.miquido.android.navigation.handler

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
internal class NavigationViewModel @Inject constructor(
    private val navigation: Navigation,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navEntryId = savedStateHandle.getNavEntryId()

    val navActions: Flow<NavAction> = navigation.navActions(navEntryId)
        .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed())

    val navResults: Flow<NavResult> = navigation.navResults(navEntryId)

    val resultLaunches: Flow<NavResultLaunch> = navigation.resultLaunches(navEntryId)

    val resultCallbacks: Flow<Set<NavResultCallback>> = navigation.resultCallbacks(navEntryId)

    override fun onCleared() {
        super.onCleared()
        navigation.clear(navEntryId)
    }
}
