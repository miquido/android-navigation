package com.miquido.android.navigation

import android.os.Bundle
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

const val NAV_ENTRY_ID = "nav-entry:id"

@JvmInline
value class NavEntryId(val value: String)

fun Bundle.getNavEntryId() =
    NavEntryId(requireNotNull(getString(NAV_ENTRY_ID)))

fun CreationExtras.getNavEntryId() =
    get(DEFAULT_ARGS_KEY)!!.getNavEntryId()

fun SavedStateHandle.getNavEntryId() =
    NavEntryId(requireNotNull(get<String>(NAV_ENTRY_ID)))
