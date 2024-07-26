pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Navigation"

include(":navigation-runtime")
include(":navigation-destinations")
include(":navigation-hilt")
include(":navigation-koin")

include(":sample-hilt")
include(":sample-koin")

include(":instrumented-tests")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
