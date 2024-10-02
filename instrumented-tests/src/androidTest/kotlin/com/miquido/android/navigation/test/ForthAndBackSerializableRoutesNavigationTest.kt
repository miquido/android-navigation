package com.miquido.android.navigation.test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.initializer
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.getNavEntryId
import com.miquido.android.navigation.test.activity.ForthAndBackSerializableRoutesNavigationActivity
import com.miquido.android.navigation.test.activity.ForthAndBackSerializableRoutesNavigationActivity.DestinationViewModel
import com.miquido.android.navigation.test.activity.ForthAndBackSerializableRoutesNavigationActivity.StartViewModel
import com.miquido.android.navigation.test.rules.createTestNavEntryViewModelsRule
import org.junit.Rule

class ForthAndBackSerializableRoutesNavigationTest : ForthAndBackNavigationTest<ForthAndBackSerializableRoutesNavigationActivity>() {

    @get:Rule(order = 0)
    val navEntryViewModelsRule = createTestNavEntryViewModelsRule {
        initializer {
            StartViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
        initializer {
            DestinationViewModel(Navigator(getNavigation(), getNavEntryId()))
        }
    }

    @get:Rule(order = 1)
    override val composeTestRule = createAndroidComposeRule<ForthAndBackSerializableRoutesNavigationActivity>()
}
