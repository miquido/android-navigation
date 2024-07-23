import com.miquido.configureKotlinJvm

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("lint.convention")
    id("ktlint.convention")
    id("detekt.convention")
}

configureKotlinJvm()
