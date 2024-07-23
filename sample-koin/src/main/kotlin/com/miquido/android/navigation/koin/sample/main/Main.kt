package com.miquido.android.navigation.koin.sample.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
@Suppress("LongMethod")
fun Main(
    viewModel: MainViewModel = navEntryViewModel()
) {
    val navEntryResult by viewModel.navEntryResult.collectAsState()
    val pickContactResult by viewModel.pickContactResult.collectAsState()

    MainScreen(
        navEntryResult = navEntryResult,
        pickContactResult = pickContactResult,
        onNavigateNextClicked = viewModel::navigateNext,
        onNavigateForResultClicked = viewModel::navigateForResult,
        onPickContactClicked = viewModel::pickContact
    )
}
