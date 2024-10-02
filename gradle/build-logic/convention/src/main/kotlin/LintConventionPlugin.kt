import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.miquido.projectTargetSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class LintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                when {
                    hasPlugin("com.android.application") -> extensions
                        .getByType<ApplicationExtension>().lint.configure(projectTargetSdk)

                    hasPlugin("com.android.library") -> extensions
                        .getByType<LibraryExtension>().lint.configure(projectTargetSdk)

                    else -> {
                        pluginManager.apply("com.android.lint")
                        extensions.getByType<Lint>().configure(projectTargetSdk)
                    }
                }
            }
        }
    }

    private fun Lint.configure(projectTargetSdk: Int) {
        targetSdk = projectTargetSdk
        xmlReport = true
        checkDependencies = true
        checkReleaseBuilds = false
        ignoreTestSources = true
        warningsAsErrors = true
        disable += setOf(
            "GoogleAppIndexingWarning",
            "GradleDependency",
            "JavaPluginLanguageLevel",
            "AndroidGradlePluginVersion",
            "EnsureInitializerMetadata",
            "ObsoleteLintCustomCheck",
            "VectorPath"
        )
    }
}
