package com.miquido.android.navigation.viewmodel

import androidx.annotation.VisibleForTesting

object NavEntryViewModelProvider {

    private var isResetting = false

    var factory: NavEntryViewModelFactory? = null
        set(value) {
            if (field != null && !isResetting) throw IllegalStateException("It is not allowed to override view model factory!")
            if (value == null && !isResetting) throw IllegalStateException("It is not allowed to set null view model factory!")
            field = value
        }

    @VisibleForTesting
    fun reset() {
        isResetting = true
        factory = null
        isResetting = false
    }
}
