import com.android.build.api.dsl.LibraryExtension
import com.miquido.projectVersionName

plugins {
    id("maven-publish")
    id("signing")
}

group = "com.miquido.android.navigation"
version = projectVersionName

if (pluginManager.hasPlugin("com.android.library")) {
    extensions.configure<LibraryExtension> {
        publishing {
            singleVariant("release") {
                withSourcesJar()
                withJavadocJar()
            }
        }
    }
    afterEvaluate {
        setupPublishing(components["release"])
    }
}

if (pluginManager.hasPlugin("java-library")) {
    extensions.configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }
    afterEvaluate {
        setupPublishing(components["java"])
    }
}

fun setupPublishing(component: SoftwareComponent) {
    publishing {
        repositories {
            maven {
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    // Credentials should be stored in the gradle.properties file in the user’s Gradle home directory
                    username = findProperty("sonatype.user").toString()
                    password = findProperty("sonatype.password").toString()
                }
            }
        }
        publications {
            register<MavenPublication>("release") {
                groupId = project.group.toString()
                version = project.version.toString()
                from(component)

                pom {
                    name = "Compose Navigator"
                    description = "AndroidX Navigation controlled from view models."
                    url = "https://github.com/miquido/android-navigation"
                    licenses {
                        license {
                            name = "The Apache License, Version 2.0"
                            url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                        }
                    }
                    developers {
                        developer {
                            id = "roman.savka"
                            name = "Roman Savka"
                            email = "roman.savka@miquido.com"
                        }
                    }
                    scm {
                        connection = "scm:git:git://github.com:miquido/android-navigation.git"
                        developerConnection = "scm:git:ssh://github.com:miquido/android-navigation.git"
                        url = "https://github.com/miquido/android-navigation"
                    }
                }
            }
        }
    }
}

signing {
    // Credentials should be configured according to
    // https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
    sign(publishing.publications)
}
