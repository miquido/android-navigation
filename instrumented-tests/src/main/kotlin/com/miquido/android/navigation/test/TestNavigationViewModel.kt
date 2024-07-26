package com.miquido.android.navigation.test

import androidx.lifecycle.SavedStateHandle
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel

class TestNavigationViewModel(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle
) : AbstractNavigationViewModel(navigation, savedStateHandle)
