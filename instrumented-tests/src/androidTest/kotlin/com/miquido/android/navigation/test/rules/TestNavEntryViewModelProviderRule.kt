package com.miquido.android.navigation.test.rules

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.test.TestNavEntryViewModelFactory
import com.miquido.android.navigation.test.TestViewModelFactory
import com.miquido.android.navigation.viewmodel.NavEntryViewModelProvider
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

fun createTestNavEntryViewModelsRule(
    builder: InitializerViewModelFactoryBuilder.() -> Unit = {}
): TestRule = TestNavEntryViewModelProviderRule(builder)

private class TestNavEntryViewModelProviderRule(
    private val builder: InitializerViewModelFactoryBuilder.() -> Unit = {}
) : TestRule {

    private val navigation: Navigation = Navigation.Default()

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                NavEntryViewModelProvider.factory = TestNavEntryViewModelFactory(
                    navigation,
                    TestViewModelFactory(builder)
                )
                try {
                    base.evaluate()
                } finally {
                    NavEntryViewModelProvider.reset()
                }
            }
        }
    }
}
