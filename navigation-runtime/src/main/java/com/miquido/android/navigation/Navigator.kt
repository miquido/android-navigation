package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

/**
 * [Navigator] is the class responsible for:
 * - collecting navigation commands (which later utilized by [androidx.navigation.NavController])
 * - handling activity results (using [androidx.activity.result.ActivityResultRegistry])
 * - receiving and sending data between nav entries (using [androidx.navigation.NavBackStackEntry.savedStateHandle])
 */
interface Navigator {
    /**
     * Navigate to a route in the current NavGraph. If an invalid route is given, an IllegalArgumentException will be thrown.
     *
     * @param direction route for the destination
     * @param builder DSL for constructing a new [androidx.navigation.NavOptions]
     *
     * @see [androidx.navigation.NavController.navigate]
     */
    suspend fun navigate(direction: Direction, builder: NavOptionsBuilder.() -> Unit = {})

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
    suspend fun popBackStack(route: Route, inclusive: Boolean, saveState: Boolean = false)

    /**
     * Attempts to navigate up in the navigation hierarchy.
     * @see [androidx.navigation.NavController.navigateUp]
     */
    suspend fun navigateUp()

    /**
     * Publish the result to the previous backstack entry.
     */
    suspend fun <R : Any> setNavResult(result: R)

    /**
     * Launch [ActivityResultContract] which was previously registered using [Navigator.registerForResult].
     * Selection of contract to launch is based on its [KClass].
     *
     * @param contract contract type to launch
     * @param input input data for contract
     */
    suspend fun <I> launchForResult(contract: KClass<out ActivityResultContract<I, *>>, input: I)

    /**
     * Register backward result expectations from the specified route.
     *
     * @param origin route which is expected to publish backward result
     * @param type type of expected result
     * @return flow of results. Received `null`'s means cancellation.
     */
    fun <O : Any> registerForResult(origin: Route, type: KClass<O>): Flow<O?>

    /**
     * Register new [ActivityResultContract] in [androidx.activity.result.ActivityResultRegistry].
     *
     * @param contract contract to register
     * @return flow of results. Received `null`'s means cancellation.
     */
    fun <I, O> registerForResult(contract: ActivityResultContract<I, O>): Flow<O?>
}

@JvmName("launchForResultWithVoidInput")
suspend fun Navigator.launchForResult(contract: KClass<out ActivityResultContract<Void?, *>>) = launchForResult(contract, null)

@JvmName("launchForResultWithUnitInput")
suspend fun Navigator.launchForResult(contract: KClass<out ActivityResultContract<Unit, *>>) = launchForResult(contract, Unit)
