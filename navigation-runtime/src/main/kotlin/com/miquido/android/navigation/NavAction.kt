package com.miquido.android.navigation

import android.net.Uri
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

val EMPTY_OPTIONS = navOptions {}

sealed class NavAction {
    @ConsistentCopyVisibility
    data class NavigateTo private constructor(
        val routeString: String? = null,
        val routeAny: Any? = null,
        val options: NavOptions = EMPTY_OPTIONS
    ) : NavAction() {

        constructor(route: String, options: NavOptions = EMPTY_OPTIONS) : this(
            routeString = route,
            options = options
        )

        constructor(route: Any, options: NavOptions = EMPTY_OPTIONS) : this(
            routeAny = route,
            options = options
        )

        companion object
    }

    data class Deeplink(
        val uri: Uri,
        val options: NavOptions = EMPTY_OPTIONS
    ) : NavAction()

    @ConsistentCopyVisibility
    data class PopBackTo private constructor(
        val routeString: String? = null,
        val routeAny: Any? = null,
        val inclusive: Boolean = false,
        val saveState: Boolean = false
    ) : NavAction() {

        constructor(route: String, inclusive: Boolean, saveState: Boolean = false) : this(
            routeString = route,
            inclusive = inclusive,
            saveState = saveState
        )

        constructor(route: Any, inclusive: Boolean, saveState: Boolean = false) : this(
            routeAny = route,
            inclusive = inclusive,
            saveState = saveState
        )

        companion object
    }

    object NavigateUp : NavAction()
}
