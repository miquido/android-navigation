package com.miquido.android.navigation.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation.LANDSCAPE
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Test

abstract class NavEntryBackResultTest<A : ComponentActivity>(
    private val withOrientationChange: Boolean
) {

    abstract val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>

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
}
