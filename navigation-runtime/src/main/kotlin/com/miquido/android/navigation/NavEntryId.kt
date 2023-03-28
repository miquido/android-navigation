package com.miquido.android.navigation

import androidx.lifecycle.SavedStateHandle

internal const val NAV_ENTRY_ID = "nav-entry:id"

@JvmInline
internal value class NavEntryId(val value: String)

internal fun SavedStateHandle.getNavEntryId() =
    NavEntryId(requireNotNull(get<String>(NAV_ENTRY_ID)))
