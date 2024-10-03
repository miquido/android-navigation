@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.miquido.android.navigation

import androidx.navigation.NavType
import androidx.navigation.serialization.generateRoutePattern
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KType

@OptIn(InternalSerializationApi::class)
internal fun generateRoutePattern(
    route: Any,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap()
): String =
    route::class.serializer().generateRoutePattern(typeMap)
