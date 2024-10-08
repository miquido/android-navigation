package com.miquido.android.navigation.koin.sample.main

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.koin.sample.NextRoute
import com.miquido.android.navigation.koin.sample.ResultRoute
import com.miquido.android.navigation.launchForResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val navigator: Navigator
) : ViewModel() {

    val navEntryResult: StateFlow<String?> = navigator
        .registerForResult(ResultRoute, String::class)
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    val pickContactResult: StateFlow<Uri?> = navigator
        .registerForResult(ActivityResultContracts.PickContact())
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    fun navigateNext() = viewModelScope.launch {
        navigator.navigate(NextRoute)
    }

    fun navigateForResult() = viewModelScope.launch {
        navigator.navigate(ResultRoute)
    }

    fun pickContact() = viewModelScope.launch {
        navigator.launchForResult(ActivityResultContracts.PickContact::class)
    }
}
