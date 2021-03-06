buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidPlugin}")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.1")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.4.1")
    }
}

allprojects {
    val ktlint = configurations.create("ktlint")

    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io/")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/soywiz/soywiz")
        maven(url = "https://dl.bintray.com/lisawray/maven")
    }

    dependencies {
        ktlint("com.github.shyiko:ktlint:0.30.0")
    }

    task<JavaExec>("ktlintCheck") {
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args = listOf("src/**/*.kt", "src/**/*Test.kt", "-a")
    }

    task<JavaExec>("ktlintFormat") {
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args = listOf("src/**/*.kt", "src/**/*Test.kt", "-a", "-F")
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            // freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            // freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.FlowPreview"
            // freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
            // freeCompilerArgs += "-Xallow-jvm-ir-dependencies"
            // freeCompilerArgs += "-Xskip-prerelease-check"
            // freeCompilerArgs += "-Xskip-metadata-version-check"
            // freeCompilerArgs += "-Xjvm-default=enable"

            jvmTarget = "1.8"
        }
    }
}

apply(plugin = "io.gitlab.arturbosch.detekt")
