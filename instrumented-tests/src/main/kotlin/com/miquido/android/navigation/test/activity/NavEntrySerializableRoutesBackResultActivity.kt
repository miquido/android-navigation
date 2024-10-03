package com.miquido.android.navigation.test.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.composable
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.test.PUBLISHER_ROUT
import com.miquido.android.navigation.test.RECEIVER_ROUT
import com.miquido.android.navigation.test.ui.ActivityRoot
import com.miquido.android.navigation.test.ui.SingleActionScreen
import com.miquido.android.navigation.viewmodel.navEntryViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class NavEntrySerializableRoutesBackResultActivity : ComponentActivity() {

    @Serializable
    data object ReceiverRoute

    @Serializable
    data object PublisherRoute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() = MaterialTheme {
        ActivityRoot(ReceiverRoute) {
            composable<ReceiverRoute> { navEntry ->
                val vm = navEntryViewModel<ReceiverViewModel>(navEntry)
                val result by vm.publisherResults.collectAsState()
                SingleActionScreen(RECEIVER_ROUT, "Navigate for result", vm::navigateToPublisher) {
                    Text(
                        text = buildAnnotatedString {
                            append("Publisher result: ")
                            append(result.toString())
                        }
                    )
                }
            }
            composable<PublisherRoute> {
                val vm = navEntryViewModel<PublisherViewModel>(it)
                SingleActionScreen(PUBLISHER_ROUT, "Navigate Up with result", vm::navigateToUpWithResult)
            }
        }
    }

    class ReceiverViewModel(
        private val navigator: Navigator
    ) : ViewModel() {

        val publisherResults: StateFlow<String?> = navigator
            .registerForResult(PublisherRoute, String::class)
            .stateIn(
                viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = "no-data"
            )

        fun navigateToPublisher() = viewModelScope.launch {
            navigator.navigate(PublisherRoute)
        }
    }

    class PublisherViewModel(
        private val navigator: Navigator
    ) : ViewModel() {

        fun navigateToUpWithResult() = viewModelScope.launch {
            navigator.setNavResult("@result-data@")
            navigator.navigateUp()
        }
    }
}
