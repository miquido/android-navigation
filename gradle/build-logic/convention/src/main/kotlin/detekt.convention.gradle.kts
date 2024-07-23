import com.miquido.libs

val detektConfiguration: Configuration by configurations.creating

dependencies {
    detektConfiguration(libs.findLibrary("detekt").get())
    detektConfiguration(libs.findLibrary("detekt-rules-libraries").get())
}

tasks.register("detekt", JavaExec::class) {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = detektConfiguration
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    inputs.files(fileTree("src").matching {
        include("**/*.kt")
        exclude("test/**/*.kt", "androidTest/**/*.kt")
    })
    outputs.file("build/reports/detekt-report.xml")
    outputs.cacheIf { true }

    args = listOf(
        "--input", "src",
        "--excludes", "src/test/**,src/androidTest/**",
        "--config", "$rootDir/.codeStyleConfig/detekt-config.yml",
        "--report", "xml:build/reports/detekt-report.xml,html:build/reports/detekt-report.html"
    )

    maxHeapSize = "256m"
}
