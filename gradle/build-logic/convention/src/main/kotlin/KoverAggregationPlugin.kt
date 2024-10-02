import com.miquido.setupKover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverAggregationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlinx.kover")
        }
        extensions.configure<KoverProjectExtension> {
            setupKover(this)

            merge {
                subprojects {
                    it.hasSources()
                }

                // report executing 'jvm' (java libraries) and 'debug' (android libraries) variants
                createVariant("main") {
                    add("debug", optional = true)
                    add("jvm", optional = true)
                }
            }
        }
    }

    private fun Project.hasSources(): Boolean =
        file("${projectDir}/src/main/kotlin").exists()
}
