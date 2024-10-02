package com.miquido

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.projectCompileSdk
    get() = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
val Project.projectMinSdk
    get() = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
val Project.projectTargetSdk
    get() = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
val Project.projectVersionName: String
    get() = libs.findVersion("android-version-versionName").get().requiredVersion

val VersionCatalog.androidApplicationPluginId
    get() = pluginId("android-application")

val VersionCatalog.androidLibraryPluginId
    get() = pluginId("android-library")

val VersionCatalog.kotlinAndroidPluginId
    get() = pluginId("kotlin-android")

val VersionCatalog.kotlinComposePluginId
    get() = pluginId("kotlin-compose")

private fun VersionCatalog.pluginId(alias: String): String = findPlugin(alias).get().get().pluginId
