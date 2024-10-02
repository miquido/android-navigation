import com.android.build.api.dsl.ApplicationExtension
import com.miquido.androidApplicationPluginId
import com.miquido.configureAndroid
import com.miquido.configureKotlinAndroid
import com.miquido.kotlinAndroidPluginId
import com.miquido.libs
import com.miquido.projectTargetSdk
import com.miquido.setupKover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.androidApplicationPluginId)
            apply(libs.kotlinAndroidPluginId)
            apply("lint.convention")
            apply("ktlint.convention")
            apply("detekt.convention")
            apply("org.jetbrains.kotlinx.kover")
        }

        extensions.configure<ApplicationExtension> {
            defaultConfig.targetSdk = projectTargetSdk
            configureAndroid(this)
            configureKotlinAndroid()
        }
        extensions.configure<KoverProjectExtension> {
            setupKover(this)
        }
    }
}
