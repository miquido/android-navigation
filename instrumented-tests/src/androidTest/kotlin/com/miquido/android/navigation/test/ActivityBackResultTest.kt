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
import com.miquido.android.navigation.test.activity.ActivityBackResultReceiverActivity
import com.miquido.android.navigation.test.rules.createTestNavEntryViewModelsRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ActivityBackResultTest(
    private val withOrientationChange: Boolean
) {

    @get:Rule(order = 0)
    val navEntryViewModelsRule = createTestNavEntryViewModelsRule {
        initializer {
            ActivityBackResultReceiverActivity.ReceiverViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
    }

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ActivityBackResultReceiverActivity>()

    @get:Rule(order = 2)
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(PORTRAIT)

    @Test
    fun receiverNavEntryDisplaysResultProvidedByPublishingActivity(): Unit = with(composeTestRule) {
        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Activity result: no-data")
            .assertIsDisplayed()
        onNodeWithText("Launch activity for result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$PUBLISHER_ROUT` Screen!")
            .assertIsDisplayed()
        if (withOrientationChange) onDevice().setScreenOrientation(LANDSCAPE)
        onNodeWithText("Publish Activity Result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Activity result: @result-data@")
            .assertIsDisplayed()
    }

    @Test
    fun receiverNavEntryDisplaysNullResultWhenPublishingActivityClosed(): Unit = with(composeTestRule) {
        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Activity result: no-data")
            .assertIsDisplayed()
        onNodeWithText("Launch activity for result")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$PUBLISHER_ROUT` Screen!")
            .assertIsDisplayed()
        if (withOrientationChange) onDevice().setScreenOrientation(LANDSCAPE)
        Espresso.pressBack()

        onNodeWithText("I am `$RECEIVER_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Activity result: null")
            .assertIsDisplayed()
    }

    companion object {
        @Parameters
        @JvmStatic
        fun data() = listOf(false, true)
    }

}
