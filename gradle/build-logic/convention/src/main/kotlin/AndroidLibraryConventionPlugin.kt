import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.miquido.androidLibraryPluginId
import com.miquido.configureAndroid
import com.miquido.configureKotlinAndroid
import com.miquido.disableUnnecessaryAndroidTests
import com.miquido.kotlinAndroidPluginId
import com.miquido.libs
import com.miquido.projectTargetSdk
import com.miquido.setupKover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.androidLibraryPluginId)
            apply(libs.kotlinAndroidPluginId)
            apply("lint.convention")
            apply("ktlint.convention")
            apply("detekt.convention")
            apply("org.jetbrains.kotlinx.kover")
        }

        extensions.configure<LibraryExtension> {
            configureAndroid(this)
            configureKotlinAndroid()

            testOptions.targetSdk = projectTargetSdk
        }
        extensions.configure<LibraryAndroidComponentsExtension> {
            disableUnnecessaryAndroidTests(this)
        }
        extensions.configure<KoverProjectExtension> {
            setupKover(this)
        }
    }
}
