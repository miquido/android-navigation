package com.miquido.android.navigation

import android.content.Context
import androidx.startup.Initializer
import com.miquido.android.navigation.viewmodel.NavEntryViewModelProvider

@Suppress("UNUSED")
internal class HiltNavEntryViewModelInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        NavEntryViewModelProvider.factory = HiltNavEntryViewModelFactory()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
