package com.miquido.android.navigation.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.initializer
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.getNavEntryId
import com.miquido.android.navigation.test.activity.ForthAndBackNavigationActivity
import com.miquido.android.navigation.test.activity.ForthAndBackNavigationActivity.Companion.DESTINATION_ROUT
import com.miquido.android.navigation.test.activity.ForthAndBackNavigationActivity.Companion.START_ROUT
import com.miquido.android.navigation.test.rules.createTestNavEntryViewModelsRule
import org.junit.Rule
import org.junit.Test

class ForthAndBackNavigationTest {

    @get:Rule(order = 0)
    val navEntryViewModelsRule = createTestNavEntryViewModelsRule {
        initializer {
            ForthAndBackNavigationActivity.StartViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
        initializer {
            ForthAndBackNavigationActivity.DestinationViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
    }

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ForthAndBackNavigationActivity>()

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
