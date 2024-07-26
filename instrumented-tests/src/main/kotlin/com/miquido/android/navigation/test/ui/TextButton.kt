package com.miquido.android.navigation.test.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextButton(
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
