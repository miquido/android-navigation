package com.miquido.android.navigation.viewmodel

object NavEntryViewModelProvider {
    var factory: NavEntryViewModelFactory? = null
        set(value) {
            if (field != null) throw IllegalStateException("It is not allowed to override view model factory!")
            if (value == null) throw IllegalStateException("It is not allowed to set null view model factory!")
            field = value
        }
}
