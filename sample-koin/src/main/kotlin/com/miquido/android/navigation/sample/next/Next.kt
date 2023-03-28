package com.miquido.android.navigation.sample.next

import androidx.compose.runtime.Composable
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun Next(
    viewModel: NextViewModel = navEntryViewModel()
) = NextScreen(onNavigateUp = viewModel::navigateUp)
