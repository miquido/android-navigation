import com.miquido.configureKotlinJvm
import com.miquido.setupKover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
            apply("lint.convention")
            apply("ktlint.convention")
            apply("detekt.convention")
            apply("org.jetbrains.kotlinx.kover")
        }
        configureKotlinJvm()

        tasks.withType<Test> {
            useJUnitPlatform()
            jvmArgs(jvmArgs.orEmpty() + "-Xshare:off")
            maxParallelForks = Runtime.getRuntime().availableProcessors() - 1
        }

        extensions.configure<KoverProjectExtension> {
            setupKover(this)
        }
    }
}

