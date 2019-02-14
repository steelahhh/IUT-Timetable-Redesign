@file:Suppress("unused")

object ApplicationID {
    const val default = "com.alefimenko.iuttimetable"
    const val develop = "com.alefimenko.iuttimetable.develop"
}

object Versions {
    const val minSdk = 19
    const val targetSdk = 28
    const val compileSdk = 28
    const val appVersionCode = 3000
    const val appVersionName = "2.2.0"

    const val kotlin = "1.3.21"
    const val androidPlugin = "3.5.0-alpha03"

    const val badoo = "1.1.4"
    const val aac = "2.0.0"
    const val rxKotlin = "2.2.0"
    const val rxAndroid = "2.0.2"
    const val room = "2.1.0-alpha02"

    const val leakCanary = "1.6.3"

    const val jsoup = "1.11.3"
    const val fastadapter = "3.3.1"
    const val retrofit = "2.4.0"
    const val gson = "2.8.5"
    const val koin = "1.0.2"
    const val timber = "4.7.1"
    const val materialDialogs = "2.0.0"
    const val junit = "4.12"
    const val testRunner = "1.1.0"
    const val espresso = "3.1.0"
}

object Deps {
    const val appcompat = "androidx.appcompat:appcompat:1.0.2"
    const val androidxCore = "androidx.core:core-ktx:1.0.1"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
    const val material = "com.google.android.material:material:1.1.0-alpha03"
    const val constraint = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val multidex = "com.android.support:multidex:1.0.3"

    const val databindingCompiler = "com.android.databinding:compiler:${Versions.androidPlugin}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.aac}"
    const val lifecycleReactive = "androidx.lifecycle:lifecycle-reactivestreams:${Versions.aac}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.aac}"

    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.room}"

    const val fastAdapterCore = "com.mikepenz:fastadapter:${Versions.fastadapter}"
    const val fastAdapterCommons = "com.mikepenz:fastadapter-commons:${Versions.fastadapter}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"

    val koin = Koin

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val ext = "org.koin:koin-core-ext:${Versions.koin}"
        const val test = "org.koin:koin-test:${Versions.koin}"
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val androidScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    }

    const val mviCore = "com.github.badoo.mvicore:mvicore:${Versions.badoo}"
    const val mviCoreAndroid = "com.github.badoo.mvicore:mvicore-android:${Versions.badoo}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val leakCanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val materialDialogs = "com.afollestad.material-dialogs:core:${Versions.materialDialogs}"

    const val junit = "junit:junit:${Versions.junit}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}