object Versions {
    val kotlin_version = "1.4.10"
    val kotlin_gradle_plugin = "1.4.10"
    val core_ktx = "1.3.2"
    val appcompat = "1.2.0"
    val constraintlayout = "2.0.2"
    val material = "1.3.0-alpha03"
    val lottie = "3.4.4"

    val junit = "4.13"
    val ext_junit = "1.1.2"
    val espresso_core = "3.3.0"

    val kotlinx_coroutines_core = "1.3.9"
    val kotlinx_coroutines_android = "1.3.7"
    val recyclerview = "1.1.0"
    val eventbus = "3.1.1"
    val multidex = "1.0.3"
    val okhttp3_integration = "4.9.0"
    val retrofit = "2.9.0"
    val converter_gson = "2.9.0"
    val converter_scalars = "2.6.2"
    val logging_interceptor = "4.2.0"

    val glide_compiler = "4.9.0"
    val glide_transformations = "4.1.0"
    val flycoTabLayout = "2.1.2@aar"
    val viewpager2 = "1.0.0"

    val lifecycle = "2.2.0"
    val datastore = "1.0.0-alpha02"
    val smart_refresh_layout = "2.0.1"

    val room = "2.2.5"
}

object Deps {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    val kotlin_gradle_plugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle_plugin}"
    val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val material = "com.google.android.material:material:${Versions.material}"
    val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    val junit = "junit:junit:${Versions.junit}"
    val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_core}"
    val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val eventbus = "org.greenrobot:eventbus:${Versions.eventbus}"
    val multidex = "com.android.support:multidex:${Versions.multidex}"
    val okhttp3_integration =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.okhttp3_integration}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converter_gson}"
    val converter_scalars = "com.squareup.retrofit2:converter-scalars:${Versions.converter_scalars}"
    val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"

    val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_compiler}"
    val glide_transformations =
        "jp.wasabeef:glide-transformations:${Versions.glide_transformations}"
    val flycoTabLayout = "com.flyco.tablayout:FlycoTabLayout_Lib:${Versions.flycoTabLayout}"
    val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"

    val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

    val smart_refresh_layout =
        "com.scwang.smart:refresh-layout-kernel:${Versions.smart_refresh_layout}"
    val smart_refresh_header =
        "com.scwang.smart:refresh-header-material:${Versions.smart_refresh_layout}"
    val smart_refresh_footer =
        "com.scwang.smart:refresh-footer-classics:${Versions.smart_refresh_layout}"
    val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"
}