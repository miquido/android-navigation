import com.miquido.configureAndroid
import com.miquido.configureKotlinAndroid
import com.miquido.projectTargetSdk

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("lint.convention")
    id("ktlint.convention")
    id("detekt.convention")
}

android {
    defaultConfig.targetSdk = projectTargetSdk

    configureAndroid(this)
    configureKotlinAndroid()
}
