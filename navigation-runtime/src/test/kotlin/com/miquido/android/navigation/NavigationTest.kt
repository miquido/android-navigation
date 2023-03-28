package com.miquido.android.navigation

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts.PickContact
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import app.cash.turbine.test
import app.cash.turbine.testIn
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class NavigationTest {

    private val navigation = NavigationImpl()

    private val navigator = NavigatorImpl(navigation, NavEntryId("nav-entry"))

    @Test
    fun `navigator navigate is emitted as nav to action`() = runTest {
        val direction = "dashboard"
        val options: NavOptionsBuilder.() -> Unit = {
            popUpTo("authentication") {
                inclusive = true
            }
        }

        navigator.navigate(direction, options)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavAction.To(direction, navOptions(options)))
        }
    }

    @Test
    fun `navigator pop back stack is emitted as nav pop action`() = runTest {
        val route = "authentication"

        navigator.popBackStack(route, inclusive = false)

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavAction.Pop(route, inclusive = false))
        }
    }

    @Test
    fun `navigator navigate up is emitted as nav up action`() = runTest {
        navigator.navigateUp()

        navigation.navActions(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavAction.Up)
        }
    }

    @Test
    fun `navigator set result is emitted as nav result`() = runTest {
        navigator.setNavResult("result")

        navigation.navResults(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavResult("result"))
        }
    }

    @Test
    fun `navigator launch for result is emitted as nav result launch`() = runTest {
        navigator.launchForResult(PickContact::class, null)

        navigation.resultLaunches(NavEntryId("nav-entry")).test {
            assertThat(awaitItem()).isEqualTo(NavResultLaunch.create(PickContact::class, null))
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `navigator activity result callback is automatically stored in and remove from result callbacks`() = runTest {
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

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `navigator activity result flow emits value when callback invoked`() = runTest {
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


    @Suppress("UNCHECKED_CAST")
    @Test
    fun `navigator nav entry result callback is automatically stored in and remove from result callbacks`() = runTest {
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

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `navigator nav entry result flow emits value when callback invoked`() = runTest {
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
