package com.miquido.android.navigation.handler

import androidx.lifecycle.SavedStateHandle
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel

internal class KoinNavigationViewModel(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle
) : AbstractNavigationViewModel(navigation, savedStateHandle)
