package com.miquido.android.navigation.test

import androidx.lifecycle.viewmodel.CreationExtras
import com.miquido.android.navigation.Navigation

@JvmField
val NAVIGATION_KEY = object : CreationExtras.Key<Navigation> {}

fun CreationExtras.getNavigation(): Navigation =
    requireNotNull(get(NAVIGATION_KEY))
