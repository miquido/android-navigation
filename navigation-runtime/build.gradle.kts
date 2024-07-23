plugins {
    id("android-library.convention")
    id("android-library.compose.convention")
    id("publishing.convention")
}

android {
    namespace = "com.miquido.android.navigation.runtime"
}

dependencies {
    implementation (libs.androidx.navigation.runtime)
    implementation (libs.androidx.navigation.compose)

    implementation (libs.kotlinx.coroutines.core)

    testImplementation (libs.junit)
    testImplementation (libs.mockk)
    testImplementation (libs.truth)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.turbine)
}
