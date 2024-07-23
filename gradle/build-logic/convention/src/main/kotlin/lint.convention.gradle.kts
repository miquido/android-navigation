import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.miquido.projectTargetSdk

with(pluginManager) {
    when {
        hasPlugin("com.android.application") -> extensions
            .getByType<ApplicationExtension>().lint.configure()

        hasPlugin("com.android.library") -> extensions
            .getByType<LibraryExtension>().lint.configure()

        else -> {
            pluginManager.apply("com.android.lint")
            extensions.getByType<Lint>().configure()
        }
    }
}

private fun Lint.configure() {
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
        "EnsureInitializerMetadata"
    )
}
