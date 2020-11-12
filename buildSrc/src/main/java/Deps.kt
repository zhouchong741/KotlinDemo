object Versions {
    const val kotlin_version = "1.4.10"
    const val kotlin_gradle_plugin = "1.4.10"
    const val core_ktx = "1.3.2"
    const val appcompat = "1.2.0"
    const val constraintlayout = "2.0.2"
    const val material = "1.3.0-alpha03"
    const val lottie = "3.4.4"

    const val junit = "4.13"
    const val ext_junit = "1.1.2"
    const val espresso_core = "3.3.0"

    const val kotlinx_coroutines_core = "1.3.9"
    const val kotlinx_coroutines_android = "1.3.7"
    const val recyclerview = "1.1.0"
    const val multidex = "1.0.3"
    const val okhttp3_integration = "4.9.0"
    const val retrofit = "2.9.0"
    const val converter_gson = "2.9.0"
    const val converter_scalars = "2.6.2"
    const val logging_interceptor = "4.9.0"

    const val glide = "4.11.0"
    const val glide_compiler = "4.11.0"
    const val glide_transformations = "4.3.0"
    const val flycoTabLayout = "2.1.2@aar"
    const val viewpager2 = "1.0.0"

    const val lifecycle = "2.2.0"
    const val datastore = "1.0.0-alpha02"
    const val smart_refresh_layout = "2.0.1"

    const val room = "2.2.5"

    const val indicator = "1.0.6"
    const val mmkv = "1.2.4"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val kotlin_gradle_plugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle_plugin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    const val junit = "junit:junit:${Versions.junit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    const val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_core}"
    const val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
    const val okhttp3_integration =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.okhttp3_integration}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converter_gson}"
    const val converter_scalars =
        "com.squareup.retrofit2:converter-scalars:${Versions.converter_scalars}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"

    const val glide = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_compiler}"
    const val glide_transformations =
        "jp.wasabeef:glide-transformations:${Versions.glide_transformations}"
    const val flycoTabLayout = "com.flyco.tablayout:FlycoTabLayout_Lib:${Versions.flycoTabLayout}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"

    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

    const val smart_refresh_layout =
        "com.scwang.smart:refresh-layout-kernel:${Versions.smart_refresh_layout}"
    const val smart_refresh_header =
        "com.scwang.smart:refresh-header-material:${Versions.smart_refresh_layout}"
    const val smart_refresh_header_classic =
        "com.scwang.smart:refresh-header-classics:${Versions.smart_refresh_layout}"
    const val smart_refresh_footer =
        "com.scwang.smart:refresh-footer-classics:${Versions.smart_refresh_layout}"
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val indicator = "com.github.zhpanvip:viewpagerindicator:${Versions.indicator}"
    const val mmkv = "com.tencent:mmkv-static:${Versions.mmkv}"
}