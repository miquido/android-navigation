package com.miquido

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Project

fun Project.setupKover(kover: KoverProjectExtension) = with(kover) {
    reports {
        filters {
            excludes {
                packages("hilt_aggregated_deps", "**.di")
                annotatedBy("*Generated*", "*Composable*")
                classes("*_Hilt*", "*Hilt_*", "*ViewModelFactory", "*Initializer", "*Composable*")
            }
        }
    }
}
