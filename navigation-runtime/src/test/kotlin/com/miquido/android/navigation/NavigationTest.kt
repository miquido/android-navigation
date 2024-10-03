package com.miquido.android.navigation

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts.PickContact
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.miquido.android.navigation.NavAction.Deeplink
import com.miquido.android.navigation.NavAction.NavigateTo
import com.miquido.android.navigation.NavAction.NavigateUp
import com.miquido.android.navigation.NavAction.PopBackTo
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test

internal class NavigationTest {

    private val navigation = NavigationImpl()

    private val navigator = NavigatorImpl(navigation, NavEntryId("nav-entry"))

    @Test
    fun `navigator stores provided previous nav entry info`() = runTest {
        val prevNavEntryInfo = NavEntryInfo(NavEntryId("prev-nav-entry"), "prev-route", null)

        navigation.setPreviousNavEntry(prevNavEntryInfo)

        assertThat(navigator.previousNavEntry).isEqualTo(prevNavEntryInfo)
    }

    @Test
    fun `navigator navigate with string route is emitted as NavigateTo action`() = runTest {
        val route = "dashboard"
        val options: NavOptionsBuilder.() -> Unit = {
            popUpTo("authentication") {
                inclusive = true
            }
        }

        navigator.navigate(route, options)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavigateTo(route, navOptions(options)))
        }
    }

    @Test
    fun `navigator navigate with serializable route is emitted as NavigateTo action`() = runTest {
        @Serializable
        class Dashboard

        @Serializable
        class Authentication

        val route = Dashboard()
        val options: NavOptionsBuilder.() -> Unit = {
            popUpTo<Authentication> {
                inclusive = true
            }
        }

        navigator.navigate(route, options)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavigateTo(route, navOptions(options)))
        }
    }

    @Test
    fun `navigator navigate with deeplink is emitted as Deeplink action`() = runTest {
        val deeplink = mockk<Uri>()
        val options: NavOptionsBuilder.() -> Unit = {
            popUpTo("authentication") {
                inclusive = true
            }
        }

        navigator.navigate(deeplink, options)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(Deeplink(deeplink, navOptions(options)))
        }
    }

    @Test
    fun `navigator pop back stack with string route is emitted as PopBackTo action`() = runTest {
        @Serializable
        class Authentication

        val route = Authentication()

        navigator.popBackStack(route, inclusive = false)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(PopBackTo(route, inclusive = false))
        }
    }

    @Test
    fun `navigator pop back stack with serializable route is emitted as PopBackTo action`() = runTest {
        val route = "authentication"

        navigator.popBackStack(route, inclusive = false)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(PopBackTo(route, inclusive = false))
        }
    }

    @Test
    fun `navigator navigate up is emitted as NavigateUp action`() = runTest {
        navigator.navigateUp()

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavigateUp)
        }
    }

    @Test
    fun `navigator set result is emitted as NavResult`() = runTest {
        navigator.setNavResult("result")

        navigation.navResults(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavResult("result"))
        }
    }

    @Test
    fun `navigator launch for result is emitted as NavResultLaunch`() = runTest {
        navigator.launchForResult(PickContact::class, null)

        navigation.resultLaunches(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavResultLaunch.create(PickContact::class, null))
        }
    }

    @Test
    fun `navigator register for activity results callback is automatically stored in and remove from result callbacks`() = runTest {
        turbineScope {
            val contract = PickContact()
            val navigatorTurbine = navigator.registerForResult(contract).testIn(this)
            val navigationTurbine = navigation.resultCallbacks(NavEntryId("nav-entry")).testIn(this)

            val callbacksOnAdd = navigationTurbine.awaitItem()
            assertThat(callbacksOnAdd).hasSize(1)
            assertThat(callbacksOnAdd.first())
                .isInstanceOf(NavResultCallback.Activity::class.java)
            assertThat((callbacksOnAdd.first() as NavResultCallback.Activity).contract)
                .isEqualTo(contract)

            navigatorTurbine.cancelAndConsumeRemainingEvents()

            val callbacksOnRemoval = navigationTurbine.awaitItem()
            assertThat(callbacksOnRemoval).hasSize(0)

            navigationTurbine.cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `navigator register for activity results flow emits value when callback invoked`() = runTest {
        turbineScope {
            val result = mockk<Uri>()
            val contract = PickContact()
            val navigatorTurbine = navigator.registerForResult(contract).testIn(this)
            val navigationTurbine = navigation.resultCallbacks(NavEntryId("nav-entry")).testIn(this)

            val callback = navigationTurbine.awaitItem().first() as NavResultCallback.Activity
            callback.onResult(result)

            val received = navigatorTurbine.awaitItem()
            assertThat(received).isEqualTo(result)

            navigatorTurbine.cancelAndConsumeRemainingEvents()

            val callbacks = navigationTurbine.awaitItem()
            assertThat(callbacks).hasSize(0)

            navigationTurbine.cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `navigator register for route results callback is automatically stored in and remove from result callbacks`() = runTest {
        turbineScope {
            val origin = "theme-switcher"
            val navigatorTurbine = navigator.registerForResult(origin, String::class).testIn(this)
            val navigationTurbine = navigation.resultCallbacks(NavEntryId("nav-entry")).testIn(this)

            val callbacksOnAdd = navigationTurbine.awaitItem()
            assertThat(callbacksOnAdd).hasSize(1)
            assertThat(callbacksOnAdd.first())
                .isInstanceOf(NavResultCallback.NavEntry::class.java)
            assertThat((callbacksOnAdd.first() as NavResultCallback.NavEntry).originRoute)
                .isEqualTo(origin)

            navigatorTurbine.cancelAndConsumeRemainingEvents()

            val callbacksOnRemoval = navigationTurbine.awaitItem()
            assertThat(callbacksOnRemoval).hasSize(0)

            navigationTurbine.cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `navigator register for string route results flow emits value when callback invoked`() = runTest {
        turbineScope {
            val result = "result"
            val navigatorTurbine = navigator.registerForResult("theme-switcher", String::class).testIn(this)
            val navigationTurbine = navigation.resultCallbacks(NavEntryId("nav-entry")).testIn(this)

            val callback = navigationTurbine.awaitItem().first() as NavResultCallback.NavEntry
            callback.onResult(result)

            val received = navigatorTurbine.awaitItem()
            assertThat(received).isEqualTo(result)

            navigatorTurbine.cancelAndConsumeRemainingEvents()

            val callbacks = navigationTurbine.awaitItem()
            assertThat(callbacks).hasSize(0)

            navigationTurbine.cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `navigator register for serializable route results flow emits value when callback invoked`() = runTest {
        @Serializable
        class ThemeSwitcher

        turbineScope {
            val result = "result"
            val navigatorTurbine = navigator.registerForResult(ThemeSwitcher(), String::class).testIn(this)
            val navigationTurbine = navigation.resultCallbacks(NavEntryId("nav-entry")).testIn(this)

            val callback = navigationTurbine.awaitItem().first() as NavResultCallback.NavEntry
            callback.onResult(result)

            val received = navigatorTurbine.awaitItem()
            assertThat(received).isEqualTo(result)

            navigatorTurbine.cancelAndConsumeRemainingEvents()

            val callbacks = navigationTurbine.awaitItem()
            assertThat(callbacks).hasSize(0)

            navigationTurbine.cancelAndConsumeRemainingEvents()
        }
    }
}
