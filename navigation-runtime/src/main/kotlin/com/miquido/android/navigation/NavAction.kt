package com.miquido.android.navigation

import androidx.navigation.NavOptions
import androidx.navigation.navOptions

val EMPTY_OPTIONS = navOptions {}

sealed class NavAction {
    data class To(
        val direction: String,
        val options: NavOptions = EMPTY_OPTIONS
    ) : NavAction() {
        companion object
    }

    data class Pop(
        val route: String,
        val inclusive: Boolean,
        val saveState: Boolean = false
    ) : NavAction() {
        companion object
    }

    object Up : NavAction()
}
