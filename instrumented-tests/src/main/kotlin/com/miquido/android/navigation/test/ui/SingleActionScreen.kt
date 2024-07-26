package com.miquido.android.navigation.test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleActionScreen(
    name: String,
    action: String,
    onAction: () -> Unit,
    additionalContent: @Composable ColumnScope.() -> Unit = {}
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "I am `$name` Screen!",
                modifier = Modifier.padding(12.dp)
            )
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .align(Alignment.CenterHorizontally),
                onClick = onAction
            ) {
                append(action)
            }
            additionalContent()
        }
    }
}
