import com.android.build.api.dsl.LibraryExtension
import com.miquido.projectVersionName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.plugins.signing.SigningExtension

class PublishingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("maven-publish")
            apply("signing")
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
                setupSigning()
            }
        }

        if (pluginManager.hasPlugin("java-library")) {
            extensions.configure<JavaPluginExtension> {
                withSourcesJar()
                withJavadocJar()
            }
            afterEvaluate {
                setupPublishing(components["java"])
                setupSigning()
            }
        }
    }

    private fun Project.setupPublishing(component: SoftwareComponent) {
        extensions.configure<PublishingExtension> {
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

    private fun Project.setupSigning() {
        extensions.configure<SigningExtension> {
            // Credentials should be configured according to
            // https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
            sign(extensions.getByType<PublishingExtension>().publications)
        }
    }
}
