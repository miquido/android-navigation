plugins {
    id("android-library.convention")
    id("publishing.convention")
}

android {
    namespace = "com.miquido.android.navigation.destinations"
}

dependencies {
    implementation (projects.navigationRuntime)

    implementation (libs.destinations.core)
}
