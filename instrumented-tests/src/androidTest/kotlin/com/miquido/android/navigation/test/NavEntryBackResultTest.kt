package com.miquido.android.navigation.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.initializer
import androidx.test.espresso.Espresso
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation.LANDSCAPE
import androidx.test.espresso.device.action.ScreenOrientation.PORTRAIT
import androidx.test.espresso.device.rules.ScreenOrientationRule
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.getNavEntryId
import com.miquido.android.navigation.test.activity.NavEntryBackResultActivity
import com.miquido.android.navigation.test.rules.createTestNavEntryViewModelsRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class NavEntryBackResultTest(
    private val withOrientationChange: Boolean
) {

    @get:Rule(order = 0)
    val navEntryViewModelsRule = createTestNavEntryViewModelsRule {
        initializer {
            NavEntryBackResultActivity.ReceiverViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
        initializer {
            NavEntryBackResultActivity.PublisherViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
    }

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<NavEntryBackResultActivity>()

    @get:Rule(order = 2)
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(PORTRAIT)

    @Test
    fun receiverNavEntryDisplaysResultProvidedByPublisher(): Unit = with(composeTestRule) {
        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Publisher result: no-data")
            .assertIsDisplayed()
        onNodeWithText("Navigate for result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$PUBLISHER_ROUT` Screen!")
            .assertIsDisplayed()
        if (withOrientationChange) onDevice().setScreenOrientation(LANDSCAPE)
        onNodeWithText("Navigate Up with result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Publisher result: @result-data@")
            .assertIsDisplayed()
    }

    @Test
    fun receiverNavEntryDisplaysNullResultWhenPublisherClosed(): Unit = with(composeTestRule) {
        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Publisher result: no-data")
            .assertIsDisplayed()
        onNodeWithText("Navigate for result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$PUBLISHER_ROUT` Screen!")
            .assertIsDisplayed()
        if (withOrientationChange) {
            onDevice().setScreenOrientation(LANDSCAPE)
            Thread.sleep(500)
        }
        Espresso.pressBack()

        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Publisher result: null")
            .assertIsDisplayed()
    }

    companion object {
        @Parameters
        @JvmStatic
        fun data() = listOf(false, true)
    }
}
