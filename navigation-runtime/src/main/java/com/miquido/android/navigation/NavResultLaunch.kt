package com.miquido.android.navigation

import androidx.activity.result.contract.ActivityResultContract
import kotlin.reflect.KClass

data class NavResultLaunch internal constructor(
    val type: KClass<out ActivityResultContract<*, *>>,
    val input: Any?
) {
    companion object {
        internal fun <I> create(
            type: KClass<out ActivityResultContract<I, *>>,
            input: I?
        ): NavResultLaunch = NavResultLaunch(type, input)
    }
}
