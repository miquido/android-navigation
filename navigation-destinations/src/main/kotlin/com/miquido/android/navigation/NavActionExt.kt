package com.miquido.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.Route

fun NavAction.NavigateTo.Companion.direction(
    direction: Direction,
    options: NavOptions = EMPTY_OPTIONS
): NavAction.NavigateTo = NavAction.NavigateTo(direction.route, options)

fun NavAction.PopBackTo.Companion.route(
    route: Route,
    inclusive: Boolean,
    saveState: Boolean = false
): NavAction.PopBackTo = NavAction.PopBackTo(route.route, inclusive, saveState)

fun NavAction.NavigateTo.direction(): Direction =
    Direction(requireNotNull(routeString) { "ramcosta.composedestinations.Direction requires a directions defined by string." })

fun NavAction.PopBackTo.route(): Route =
    RouteIml(requireNotNull(routeString) { "ramcosta.composedestinations.Route requires route defined by string." })

private data class RouteIml(
    override val route: String,
    override val baseRoute: String = route
) : DirectionDestinationSpec {
    @Composable
    @Suppress("ktlint:standard:function-expression-body")
    override fun DestinationScope<Unit>.Content() {
        throw IllegalStateException("Shouldn't be used as `Direction` to navigate.")
    }
}
