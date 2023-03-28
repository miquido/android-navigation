package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import kotlin.reflect.KClass

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
            ): NavEntry = NavEntry(originRoute, type, onResult as (Any?) -> Unit)
        }
    }
}
