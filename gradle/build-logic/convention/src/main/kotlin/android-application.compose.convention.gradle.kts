import com.miquido.configureAndroidCompose

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    configureAndroidCompose(this)
}
