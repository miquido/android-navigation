import com.miquido.setupKover

plugins {
    id("org.jetbrains.kotlinx.kover")
}

kover {
    setupKover(this)
}

dependencies {
    kover(project(":navigation-runtime"))
    kover(project(":navigation-destinations"))
    kover(project(":navigation-hilt"))
    kover(project(":navigation-koin"))
}
