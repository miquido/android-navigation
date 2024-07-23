plugins {
    id("android-application.convention")
    id("android-application.compose.convention")
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.miquido.android.navigation.hilt.sample"

    defaultConfig {
        applicationId = "com.miquido.android.navigation.hilt.sample"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation (projects.navigationRuntime)
    implementation (projects.navigationHilt)

    implementation (libs.androidx.compose.material)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
}
