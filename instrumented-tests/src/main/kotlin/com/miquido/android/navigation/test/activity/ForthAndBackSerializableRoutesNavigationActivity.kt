package com.miquido.android.navigation.test.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.composable
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.test.DESTINATION_ROUT
import com.miquido.android.navigation.test.START_ROUT
import com.miquido.android.navigation.test.ui.ActivityRoot
import com.miquido.android.navigation.test.ui.SingleActionScreen
import com.miquido.android.navigation.viewmodel.navEntryViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class ForthAndBackSerializableRoutesNavigationActivity : ComponentActivity() {

    @Serializable
    object StartRoute

    @Serializable
    object DestinationRoute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() = MaterialTheme {
        ActivityRoot(StartRoute) {
            composable<StartRoute> {
                val vm = navEntryViewModel<StartViewModel>(it)
                SingleActionScreen(START_ROUT, "Navigate", vm::navigateToDestination)
            }
            composable<DestinationRoute> {
                val vm = navEntryViewModel<DestinationViewModel>(it)
                SingleActionScreen(DESTINATION_ROUT, "Navigate Up", vm::navigateToUp)
            }
        }
    }

    class StartViewModel(
        private val navigator: Navigator
    ) : ViewModel() {
        fun navigateToDestination() = viewModelScope.launch {
            navigator.navigate(DestinationRoute)
        }
    }

    class DestinationViewModel(
        private val navigator: Navigator
    ) : ViewModel() {
        fun navigateToUp() = viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}
