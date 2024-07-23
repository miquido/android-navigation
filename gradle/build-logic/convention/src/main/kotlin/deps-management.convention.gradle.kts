import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import nl.littlerobots.vcu.plugin.VersionCatalogUpdateExtension

plugins {
    id("com.github.ben-manes.versions")
    id("nl.littlerobots.version-catalog-update")
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

private fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
