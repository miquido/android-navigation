package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.NavType
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal sealed class NavResultCallback(
    open val onResult: (Any?) -> Unit
) {
    class Activity private constructor(
        val contract: ActivityResultContract<Any, Any>,
        override val onResult: (Any?) -> Unit
    ) : NavResultCallback(onResult) {

        companion object {
            @Suppress("UNCHECKED_CAST")
            fun <I, O> create(
                contract: ActivityResultContract<I, O>,
                onResult: (O?) -> Unit
            ): Activity = Activity(contract as ActivityResultContract<Any, Any>, onResult as (Any?) -> Unit)
        }
    }

    class NavEntry private constructor(
        val originRoute: String,
        val type: KClass<*>,
        override val onResult: (Any?) -> Unit
    ) : NavResultCallback(onResult) {

        companion object {
            @Suppress("UNCHECKED_CAST")
            fun <O : Any> create(
                originRoute: String,
                type: KClass<O>,
                onResult: (O?) -> Unit
            ): NavEntry = NavEntry(
                originRoute = originRoute,
                type = type,
                onResult = onResult as (Any?) -> Unit
            )

            @Suppress("UNCHECKED_CAST")
            fun <O : Any> create(
                originRoute: Any,
                originRouteTypeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
                type: KClass<O>,
                onResult: (O?) -> Unit
            ): NavEntry = NavEntry(
                originRoute = generateRoutePattern(originRoute, originRouteTypeMap),
                type = type,
                onResult = onResult as (Any?) -> Unit
            )
        }
    }
}
