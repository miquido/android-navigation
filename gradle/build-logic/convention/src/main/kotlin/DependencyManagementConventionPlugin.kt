import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import nl.littlerobots.vcu.plugin.VersionCatalogUpdateExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class DependencyManagementConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.github.ben-manes.versions")
            apply("nl.littlerobots.version-catalog-update")
        }

        tasks.withType<DependencyUpdatesTask> {
            rejectVersionIf {
                isNonStable(candidate.version)
            }
        }

        extensions.configure<VersionCatalogUpdateExtension> {
            sortByKey = false
            keep {
                keepUnusedLibraries = true
            }
        }
    }

    private fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}
