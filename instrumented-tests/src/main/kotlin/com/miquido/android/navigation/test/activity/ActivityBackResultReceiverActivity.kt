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
import com.miquido.android.navigation.launchForResult
import com.miquido.android.navigation.test.RECEIVER_ROUT
import com.miquido.android.navigation.test.ui.ActivityRoot
import com.miquido.android.navigation.test.ui.SingleActionScreen
import com.miquido.android.navigation.viewmodel.navEntryViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivityBackResultReceiverActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }

    @Composable
    fun Content() = MaterialTheme {
        ActivityRoot(RECEIVER_ROUT) {
            composable(RECEIVER_ROUT) { navEntry ->
                val vm = navEntryViewModel<ReceiverViewModel>(navEntry)
                val result by vm.activityResults.collectAsState()
                SingleActionScreen(RECEIVER_ROUT, "Launch activity for result", vm::launchActivityForResult) {
                    Text(
                        text = buildAnnotatedString {
                            append("Activity result: ")
                            append(result.toString())
                        }
                    )
                }
            }
        }
    }

    class ReceiverViewModel(
        private val navigator: Navigator
    ) : ViewModel() {

        val activityResults: StateFlow<String?> = navigator
            .registerForResult(ActivityBackResultPublisherContract())
            .stateIn(
                viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = "no-data"
            )

        fun launchActivityForResult() = viewModelScope.launch {
            navigator.launchForResult(ActivityBackResultPublisherContract::class)
        }
    }
}
