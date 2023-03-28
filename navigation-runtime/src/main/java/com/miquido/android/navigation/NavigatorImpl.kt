package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.reflect.KClass

@ViewModelScoped
internal class NavigatorImpl internal constructor(
    private val navigation: Navigation,
    private val navEntryId: NavEntryId
) : Navigator {

    @Inject
    constructor(navigation: Navigation, savedStateHandle: SavedStateHandle) :
        this(navigation, savedStateHandle.getNavEntryId())

    override suspend fun navigate(direction: Direction, builder: NavOptionsBuilder.() -> Unit) {
        navigation.dispatchAction(navEntryId, NavAction.To(direction, navOptions(builder)))
    }

    override suspend fun popBackStack(route: Route, inclusive: Boolean, saveState: Boolean) {
        navigation.dispatchAction(navEntryId, NavAction.Pop(route, inclusive, saveState))
    }

    override suspend fun navigateUp() {
        navigation.dispatchAction(navEntryId, NavAction.Up)
    }

    override suspend fun <R : Any> setNavResult(result: R) {
        navigation.dispatchResult(navEntryId, NavResult(result))
    }

    override fun <O : Any> registerForResult(origin: Route, type: KClass<O>): Flow<O?> = callbackFlow {
        val registration = NavResultCallback.NavEntry.create(
            origin = origin,
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
