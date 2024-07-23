package com.miquido.android.navigation.koin.sample.main

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    navEntryResult: String?,
    pickContactResult: Uri?,
    onNavigateNextClicked: () -> Unit,
    onNavigateForResultClicked: () -> Unit,
    onPickContactClicked: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to Navigator Sample!",
                modifier = Modifier.padding(12.dp)
            )

            /* Simple navigation */
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .align(Alignment.CenterHorizontally),
                onClick = onNavigateNextClicked
            ) {
                append("Open Next screen")
            }

            /* Nav Entry result button */
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .align(Alignment.CenterHorizontally),
                onClick = onNavigateForResultClicked
            ) {
                append("Open Result screen")
                appendLine()
                appendLine()
                withStyle(SpanStyle(fontSize = 10.sp)) {
                    append("(Last result: $navEntryResult)")
                }
            }

            /* Activity result Button */
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .align(Alignment.CenterHorizontally),
                onClick = onPickContactClicked
            ) {
                append("Open Contact Picker")
                appendLine()
                appendLine()
                withStyle(SpanStyle(fontSize = 10.sp)) {
                    append("(Last result: $pickContactResult)")
                }
            }
        }
    }
}

@Composable
private fun TextButton(
    modifier: Modifier,
    onClick: () -> Unit,
    textBuilder: AnnotatedString.Builder.() -> Unit
) {
    Button(modifier = modifier, onClick = onClick) {
        Text(
            text = buildAnnotatedString { textBuilder(this) },
            textAlign = TextAlign.Center
        )
    }
}
