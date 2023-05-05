package com.miquido.android.navigation

import android.content.Context
import androidx.startup.Initializer
import com.miquido.android.navigation.viewmodel.NavEntryViewModelProvider

@Suppress("UNUSED")
internal class NavEntryHiltViewModelInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        NavEntryViewModelProvider.factory = NavEntryHiltViewModelFactory()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
