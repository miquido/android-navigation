package com.miquido

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension

fun setupKover(kover: KoverProjectExtension) = with(kover) {
    reports {
        filters {
            excludes {
                packages(
                    "com.miquido.android.navigation.test",
                    "com.miquido.android.navigation.hilt.sample",
                    "com.miquido.android.navigation.koin.sample",
                    "*.di",
                    "hilt_aggregated_deps",
                    "dagger.hilt.internal.aggregatedroot.codegen"
                )
                annotatedBy("*Generated*", "*Composable*")
                classes(
                    "*.BuildConfig",
                    "*Initializer",
                    "*Composable*",
                    "*_HiltModules*",
                    "*HiltWrapper_*",
                    "*_Provide*",
                )
            }
        }
    }
}
