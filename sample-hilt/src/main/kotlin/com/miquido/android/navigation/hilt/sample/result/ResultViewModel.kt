package com.miquido.android.navigation.hilt.sample.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
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
