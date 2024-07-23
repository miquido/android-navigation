plugins {
    id("android-application.convention")
    id("android-application.compose.convention")
}

android {
    namespace = "com.miquido.android.navigation.koin.sample"

    defaultConfig {
        applicationId = "com.miquido.android.navigation.koin.sample"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.navigationRuntime)
    implementation(projects.navigationKoin)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.koin.androidx.compose)
}
