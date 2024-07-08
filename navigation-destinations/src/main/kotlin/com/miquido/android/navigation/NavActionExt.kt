package com.miquido.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.Route

operator fun NavAction.To.Companion.invoke(
    direction: Direction,
    options: NavOptions = EMPTY_OPTIONS
): NavAction.To = NavAction.To(direction.route, options)

operator fun NavAction.Pop.Companion.invoke(
    route: Route,
    inclusive: Boolean,
    saveState: Boolean = false
): NavAction.Pop = NavAction.Pop(route.route, inclusive, saveState)

fun NavAction.To.direction(): Direction = Direction(direction)

fun NavAction.Pop.route(): Route = RouteIml(route)

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
