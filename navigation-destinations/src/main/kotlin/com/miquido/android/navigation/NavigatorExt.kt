package com.miquido.android.navigation

import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

/**
 * Navigate to a destination in the current NavGraph. If an invalid route is given, an IllegalArgumentException will be thrown.
 *
 * @param direction route for the destination
 * @param builder DSL for constructing a new [androidx.navigation.NavOptions]
 *
 * @see [androidx.navigation.NavController.navigate]
 */
suspend fun Navigator.navigateToDirection(direction: Direction, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(direction.route, builder)

/**
 * Attempts to pop back stack.
 *
 * @param route the topmost destination to retain
 * @param inclusive - whether the given destination should also be popped.
 * @param saveState - whether the back stack and the state of all destinations between the current destination
 * and the route should be saved for later restoration via [androidx.navigation.NavOptions]
 *
 * @see [androidx.navigation.NavController.popBackStack]
 */
suspend fun Navigator.popBackToRoute(route: Route, inclusive: Boolean, saveState: Boolean = false) =
    popBackStack(route.route, inclusive, saveState)

/**
 * Register backward result expectations from the specified route.
 *
 * @param origin route which is expected to publish backward result
 * @param type type of expected result
 * @return flow of results. Received `null`'s means cancellation.
 */
fun <O : Any> Navigator.registerForResult(origin: Route, type: KClass<O>): Flow<O?> =
    registerForResult(origin.route, type)
