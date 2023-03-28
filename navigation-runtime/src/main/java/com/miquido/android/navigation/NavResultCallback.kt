package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import com.ramcosta.composedestinations.spec.Route
import kotlin.reflect.KClass

sealed class NavResultCallback(
    open val onResult: (Any?) -> Unit
) {
    class Activity internal constructor(
        val contract: ActivityResultContract<Any, Any>,
        override val onResult: (Any?) -> Unit
    ) : NavResultCallback(onResult) {

        companion object {
            @Suppress("UNCHECKED_CAST")
            internal fun <I, O> create(
                contract: ActivityResultContract<I, O>,
                onResult: (O?) -> Unit
            ): Activity = Activity(contract as ActivityResultContract<Any, Any>, onResult as (Any?) -> Unit)
        }
    }

    class NavEntry internal constructor(
        val origin: Route,
        val type: KClass<*>,
        override val onResult: (Any?) -> Unit
    ) : NavResultCallback(onResult) {

        companion object {
            @Suppress("UNCHECKED_CAST")
            internal fun <O : Any> create(
                origin: Route,
                type: KClass<O>,
                onResult: (O?) -> Unit
            ): NavEntry = NavEntry(origin, type, onResult as (Any?) -> Unit)
        }
    }
}
