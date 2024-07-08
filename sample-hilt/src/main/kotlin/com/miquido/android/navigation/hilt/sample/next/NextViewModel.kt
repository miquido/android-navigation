package com.miquido.android.navigation.hilt.sample.next

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miquido.android.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NextViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateUp() = viewModelScope.launch {
        navigator.navigateUp()
    }
}
