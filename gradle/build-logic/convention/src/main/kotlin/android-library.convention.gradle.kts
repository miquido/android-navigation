import com.miquido.configureAndroid
import com.miquido.configureKotlinAndroid
import com.miquido.disableUnnecessaryAndroidTests
import com.miquido.projectTargetSdk

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("lint.convention")
    id("ktlint.convention")
    id("detekt.convention")
}

android {
    configureAndroid(this)
    configureKotlinAndroid()

    testOptions.targetSdk = projectTargetSdk

    androidComponents {
        disableUnnecessaryAndroidTests(this)
    }
    // The resource prefix is derived from the module name,
    // so resources inside ":core:module1" must be prefixed with "core_module1_"
    resourcePrefix = path
        .split("""\W""".toRegex())
        .drop(1)
        .distinct()
        .joinToString(separator = "_")
        .lowercase() + "_"
}
