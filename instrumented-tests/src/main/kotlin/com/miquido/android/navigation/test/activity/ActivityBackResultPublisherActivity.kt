package com.miquido.android.navigation.test.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.compose.composable
import com.miquido.android.navigation.test.ACTIVITY_RESULT_KEY
import com.miquido.android.navigation.test.PUBLISHER_ROUT
import com.miquido.android.navigation.test.ui.ActivityRoot
import com.miquido.android.navigation.test.ui.SingleActionScreen

class ActivityBackResultPublisherContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(context, ActivityBackResultPublisherActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        when (resultCode) {
            Activity.RESULT_OK -> intent?.getStringExtra(ACTIVITY_RESULT_KEY)
            Activity.RESULT_CANCELED -> null
            else -> throw IllegalStateException("Unexpected result code ; $resultCode")
        }
}

class ActivityBackResultPublisherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content(this)
        }
    }

    @Composable
    fun Content(activity: ComponentActivity) = MaterialTheme {
        ActivityRoot(startDestination = PUBLISHER_ROUT) {
            composable(PUBLISHER_ROUT) {
                SingleActionScreen(PUBLISHER_ROUT, "Publish Activity Result", onAction = {
                    with(activity) {
                        val result = bundleOf(ACTIVITY_RESULT_KEY to "@result-data@")
                        setResult(Activity.RESULT_OK, Intent().putExtras(result))
                        finish()
                    }
                })
            }
        }
    }
}
