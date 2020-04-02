plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Versions.compileSdk)
    androidExtensions {
        isExperimental = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.appVersionCode
        versionName = Versions.appVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    arrayOf(
        Modules.common
    ).forEach { dependency ->
        implementation(project(dependency))
    }

    arrayOf(
        kotlin("stdlib-jdk7", Versions.kotlin),
        Deps.timber,
        Deps.klock.core,
        Deps.klock.android,
        Deps.rxKotlin,
        Deps.rxAndroid
    ).forEach { dependency ->
        implementation(dependency)
    }

    api(Deps.room)
    kapt(Deps.roomCompiler)
    api(Deps.roomRxJava)

    api(Deps.gson)

    api(Deps.retrofit)
    api(Deps.retrofitGson)
    api(Deps.retrofitRxJava)

    api(Deps.jsoup)

    implementation(Deps.dagger.core)
    kapt(Deps.dagger.compiler)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.testRunner)
    androidTestImplementation(Deps.espresso)
}
