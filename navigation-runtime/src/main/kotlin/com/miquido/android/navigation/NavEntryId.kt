package com.miquido.android.navigation

import androidx.lifecycle.SavedStateHandle

const val NAV_ENTRY_ID = "nav-entry:id"

@JvmInline
value class NavEntryId(val value: String)

fun SavedStateHandle.getNavEntryId() =
    NavEntryId(requireNotNull(get<String>(NAV_ENTRY_ID)))
