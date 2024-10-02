plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.publishing)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.miquido.android.navigation.hilt"
}

dependencies {
    implementation (projects.navigationRuntime)

    implementation (libs.androidx.navigation.common)
    implementation (libs.androidx.navigation.runtime)
    implementation (libs.androidx.startup.runtime)

    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
}
