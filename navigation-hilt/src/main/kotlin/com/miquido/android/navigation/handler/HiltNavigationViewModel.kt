package com.miquido.android.navigation.handler

import androidx.lifecycle.SavedStateHandle
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.viewmodel.AbstractNavigationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HiltNavigationViewModel @Inject constructor(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle
) : AbstractNavigationViewModel(navigation, savedStateHandle)
