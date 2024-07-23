import com.miquido.configureAndroidCompose

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    configureAndroidCompose(this)
}
