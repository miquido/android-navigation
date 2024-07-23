package com.miquido

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) = with(commonExtension) {
    compileSdk = projectCompileSdk

    defaultConfig {
        minSdk = projectMinSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.maxParallelForks = Runtime.getRuntime().availableProcessors() - 1
            }
        }
    }
}
