package com.miquido.android.navigation

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.reflect.KClass

internal class NavigatorImpl(
    private val navigation: Navigation,
    private val navEntryId: NavEntryId
) : Navigator {

    override suspend fun navigate(direction: String, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, NavAction.To(direction, navOptions(builder)))
    }

    override suspend fun navigate(deeplink: Uri, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, NavAction.Deeplink(deeplink, navOptions(builder)))
    }

    override suspend fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean) {
        navigation.dispatchAction(navEntryId, NavAction.Pop(route, inclusive, saveState))
    }

    override suspend fun navigateUp() {
        navigation.dispatchAction(navEntryId, NavAction.Up)
    }

    override suspend fun <R : Any> setNavResult(result: R) {
        navigation.dispatchResult(navEntryId, NavResult(result))
    }

    override fun <O : Any> registerForResult(originRoute: String, type: KClass<O>): Flow<O?> = callbackFlow {
        val registration = NavResultCallback.NavEntry.create(
            originRoute = originRoute,
            type = type,
            onResult = ::trySend
        )
        navigation.addResultCallback(navEntryId, registration)
        awaitClose {
            navigation.removeResultCallback(navEntryId, registration)
        }
    }

    override fun <I, O> registerForResult(contract: ActivityResultContract<I, O>): Flow<O?> = callbackFlow {
        val registration = NavResultCallback.Activity.create(
            contract = contract,
            onResult = ::trySend
        )
        navigation.addResultCallback(navEntryId, registration)
        awaitClose {
            navigation.removeResultCallback(navEntryId, registration)
        }
    }

    override suspend fun <I> launchForResult(contract: KClass<out ActivityResultContract<I, *>>, input: I) {
        navigation.dispatchResultLaunch(navEntryId, NavResultLaunch.create(contract, input))
    }
}
