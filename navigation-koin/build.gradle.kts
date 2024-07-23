plugins {
    id("android-library.convention")
    id("android-library.compose.convention")
    id("publishing.convention")
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
