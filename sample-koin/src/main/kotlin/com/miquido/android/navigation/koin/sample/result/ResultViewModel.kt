package com.miquido.android.navigation.koin.sample.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.Navigator
import kotlinx.coroutines.launch

class ResultViewModel(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateUpWithResult() = viewModelScope.launch {
        navigator.setNavResult("!some data!")
        navigator.navigateUp()
    }

    fun close() = viewModelScope.launch {
        navigator.navigateUp()
    }
}
