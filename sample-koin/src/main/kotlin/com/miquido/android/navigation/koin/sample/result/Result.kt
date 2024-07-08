package com.miquido.android.navigation.koin.sample.result

import androidx.compose.runtime.Composable
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun Result(
    viewModel: ResultViewModel = navEntryViewModel()
) = ResultScreen(
    onNavigateUpWithResult = viewModel::navigateUpWithResult,
    onClose = viewModel::close
)
