package com.miquido.android.navigation

import android.os.Bundle

data class NavEntryInfo(
    val id: NavEntryId,
    val route: String?,
    val arguments: Bundle?
)
