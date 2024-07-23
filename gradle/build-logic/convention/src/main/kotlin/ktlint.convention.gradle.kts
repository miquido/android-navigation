import com.miquido.libs

val ktlintConfiguration: Configuration by configurations.creating

dependencies {
    ktlintConfiguration(libs.findLibrary("ktlint").get())
}

tasks.register("ktlint", JavaExec::class) {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlintConfiguration
    mainClass.set("com.pinterest.ktlint.Main")
    inputs.files(fileTree("src").matching {
        include("**/*.kt")
        exclude("test/**/*.kt", "androidTest/**/*.kt")
    })
    outputs.file("build/reports/ktlint-report.xml")
    outputs.cacheIf { true }

    args = listOf(
        "src/**/*.kt", "!src/test/**/*.kt", "!src/androidTest/**/*.kt",
        "--editorconfig=${rootDir}/.codeStyleConfig/.editorconfig",
        "--reporter=checkstyle,output=build/reports/ktlint-report.xml",
        "--reporter=plain"
    )

    maxHeapSize = "256m"
}
