package com.miquido.android.navigation.hilt.sample.next

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextScreen(
    onNavigateUp: () -> Unit
) = Box(Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "I am Next Screen!",
            modifier = Modifier.padding(12.dp)
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = onNavigateUp
        ) {
            Text(text = "Navigate Up")
        }
    }
}
