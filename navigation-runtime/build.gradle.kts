plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.publishing)
}

android {
    namespace = "com.miquido.android.navigation.runtime"
}

dependencies {
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.core)

    testImplementation(libs.bundles.test)
}
