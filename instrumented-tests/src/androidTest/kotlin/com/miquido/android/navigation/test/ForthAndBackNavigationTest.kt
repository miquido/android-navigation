package com.miquido.android.navigation.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Test

abstract class ForthAndBackNavigationTest<A : ComponentActivity> {

    abstract val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>

    @Test
    fun navigatingForthAndBackDisplaysExpectedScreens(): Unit = with(composeTestRule) {
        onNodeWithText("I am `$START_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Navigate")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$DESTINATION_ROUT` Screen!")
            .assertIsDisplayed()
        onNodeWithText("Navigate Up")
            .assertIsDisplayed()
            .performClick()

        onNodeWithText("I am `$START_ROUT` Screen!")
            .assertIsDisplayed()
    }
}
