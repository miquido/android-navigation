package com.miquido.android.navigation.sample.next

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.Navigator
import kotlinx.coroutines.launch

class NextViewModel(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateUp() = viewModelScope.launch {
        navigator.navigateUp()
    }
}
