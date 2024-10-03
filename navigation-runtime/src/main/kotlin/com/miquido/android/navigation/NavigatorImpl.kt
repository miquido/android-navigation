package com.miquido.android.navigation

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navOptions
import com.miquido.android.navigation.NavAction.Deeplink
import com.miquido.android.navigation.NavAction.NavigateTo
import com.miquido.android.navigation.NavAction.PopBackTo
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal class NavigatorImpl(
    private val navigation: Navigation,
    private val navEntryId: NavEntryId
) : Navigator {

    override val previousNavEntry: NavEntryInfo?
        get() = navigation.previousNavEntry

    override suspend fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, NavigateTo(route, navOptions(builder)))
    }

    override suspend fun navigate(route: Any, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, NavigateTo(route, navOptions(builder)))
    }

    override suspend fun navigate(deeplink: Uri, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, Deeplink(deeplink, navOptions(builder)))
    }

    override suspend fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean) {
        navigation.dispatchAction(navEntryId, PopBackTo(route, inclusive, saveState))
    }

    override suspend fun popBackStack(route: Any, inclusive: Boolean, saveState: Boolean) {
        navigation.dispatchAction(navEntryId, PopBackTo(route, inclusive, saveState))
    }

    override suspend fun navigateUp() {
        navigation.dispatchAction(navEntryId, NavAction.NavigateUp)
    }

    override suspend fun <R : Any> setNavResult(result: R) {
        navigation.dispatchResult(navEntryId, NavResult(result))
    }

    override fun <O : Any> registerForResult(
        originRoute: String,
        type: KClass<O>
    ): Flow<O?> = callbackFlow {
        registerResultCallback(
            NavResultCallback.NavEntry.create(
                originRoute = originRoute,
                type = type,
                onResult = ::trySend
            )
        )
    }

    override fun <O : Any> registerForResult(
        originRoute: Any,
        type: KClass<O>,
        originRouteTypeMap: Map<KType, NavType<*>>
    ): Flow<O?> = callbackFlow {
        registerResultCallback(
            NavResultCallback.NavEntry.create(
                originRoute = originRoute,
                originRouteTypeMap = originRouteTypeMap,
                type = type,
                onResult = ::trySend
            )
        )
    }

    override fun <I, O> registerForResult(
        contract: ActivityResultContract<I, O>
    ): Flow<O?> = callbackFlow {
        registerResultCallback(
            NavResultCallback.Activity.create(
                contract = contract,
                onResult = ::trySend
            )
        )
    }

    override suspend fun <I> launchForResult(contract: KClass<out ActivityResultContract<I, *>>, input: I) {
        navigation.dispatchResultLaunch(navEntryId, NavResultLaunch.create(contract, input))
    }

    private suspend fun ProducerScope<*>.registerResultCallback(registration: NavResultCallback) {
        navigation.addResultCallback(navEntryId, registration)
        awaitClose {
            navigation.removeResultCallback(navEntryId, registration)
        }
    }
}
