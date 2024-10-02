plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.publishing)
}

android {
    namespace = "com.miquido.android.navigation.destinations"
}

dependencies {
    implementation (projects.navigationRuntime)

    implementation (libs.destinations.core)
}
