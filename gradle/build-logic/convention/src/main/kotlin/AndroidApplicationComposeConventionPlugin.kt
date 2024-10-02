import com.android.build.api.dsl.ApplicationExtension
import com.miquido.androidApplicationPluginId
import com.miquido.configureAndroidCompose
import com.miquido.kotlinComposePluginId
import com.miquido.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.androidApplicationPluginId)
            apply(libs.kotlinComposePluginId)
        }

        extensions.configure<ApplicationExtension> {
            configureAndroidCompose(this)
        }
    }
}
