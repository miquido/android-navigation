plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.publishing)
}

android {
    namespace = "com.miquido.android.navigation.koin"
}

dependencies {
    implementation (projects.navigationRuntime)

    implementation (libs.androidx.navigation.runtime)
    implementation (libs.androidx.startup.runtime)

    implementation (libs.koin.androidx.compose)
}
