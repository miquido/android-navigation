package com.miquido.android.navigation.handler

import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miquido.android.navigation.navEntryViewModel
import com.ramcosta.composedestinations.spec.Route
import kotlin.reflect.KClass

@Composable
fun NavigationHandler(
    navController: NavController
) {
    val currentNavEntry by navController.currentBackStackEntryAsState()
    currentNavEntry?.let { navEntry ->
        val navigationViewModel = navEntryViewModel<NavigationViewModel>(navEntry)
        NavActionsHandler(
            viewModel = navigationViewModel,
            navController = navController,
            navEntry = navEntry
        )
        NavEntryResultsSender(
            viewModel = navigationViewModel,
            navController = navController,
            currNavEntry = navEntry
        )
        NavEntryResultsRecipient(
            viewModel = navigationViewModel,
            navEntry = navEntry
        )
        ActivityResultsRecipient(
            viewModel = navigationViewModel,
            navEntry = navEntry
        )
    }
}

internal fun activityResultKey(
    id: String,
    type: KClass<out ActivityResultContract<*, *>>
) = "activity-result@$id@${type.qualifiedName}"

@Suppress("FunctionOnlyReturningConstant")
internal fun navEntryRecipientOfKey() = "nav-entry@recipient-of"

internal fun <R : Any> navEntryResultKey(
    origin: Route,
    resultType: KClass<out R>
) = "nav-entry@${origin.route}@${resultType.qualifiedName}@result"

internal fun <R : Any> navEntryCanceledKey(
    origin: Route,
    resultType: KClass<out R>
) = "nav-entry@${origin.route}@${resultType.qualifiedName}@canceled"
