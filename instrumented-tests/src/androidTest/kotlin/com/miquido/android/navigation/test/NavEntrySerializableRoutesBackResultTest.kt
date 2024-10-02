package com.miquido.android.navigation.test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.initializer
import androidx.test.espresso.device.action.ScreenOrientation.PORTRAIT
import androidx.test.espresso.device.rules.ScreenOrientationRule
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.getNavEntryId
import com.miquido.android.navigation.test.activity.NavEntrySerializableRoutesBackResultActivity
import com.miquido.android.navigation.test.activity.NavEntrySerializableRoutesBackResultActivity.PublisherViewModel
import com.miquido.android.navigation.test.activity.NavEntrySerializableRoutesBackResultActivity.ReceiverViewModel
import com.miquido.android.navigation.test.rules.createTestNavEntryViewModelsRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class NavEntrySerializableRoutesBackResultTest(
    withOrientationChange: Boolean
) : NavEntryBackResultTest<NavEntrySerializableRoutesBackResultActivity>(withOrientationChange) {

    @get:Rule(order = 0)
    val navEntryViewModelsRule = createTestNavEntryViewModelsRule {
        initializer {
            ReceiverViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
        initializer {
            PublisherViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
    }

    @get:Rule(order = 1)
    override val composeTestRule = createAndroidComposeRule<NavEntrySerializableRoutesBackResultActivity>()

    @get:Rule(order = 2)
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(PORTRAIT)

    companion object {
        @Parameters
        @JvmStatic
        fun data() = listOf(false, true)
    }
}
