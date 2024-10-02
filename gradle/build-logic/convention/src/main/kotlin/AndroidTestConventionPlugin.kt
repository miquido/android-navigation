import com.android.build.gradle.TestExtension
import com.miquido.configureAndroid
import com.miquido.configureKotlinAndroid
import com.miquido.kotlinAndroidPluginId
import com.miquido.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.test")
            apply(libs.kotlinAndroidPluginId)
        }

        extensions.configure<TestExtension> {
            configureAndroid(this)
            configureKotlinAndroid()
        }
    }
}
