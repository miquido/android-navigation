import com.android.build.gradle.LibraryExtension
import com.miquido.androidLibraryPluginId
import com.miquido.configureAndroidCompose
import com.miquido.kotlinComposePluginId
import com.miquido.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.androidLibraryPluginId)
            apply(libs.kotlinComposePluginId)
        }

        extensions.configure<LibraryExtension> {
            configureAndroidCompose(this)
        }
    }
}
