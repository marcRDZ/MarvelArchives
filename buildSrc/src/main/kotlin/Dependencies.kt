const val kotlinVersion = "1.6.10"

object BuildPlugins {

    object Versions {
        const val gradle = "7.2.2"
        const val hilt = "2.41"
    }

    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hilt = "dagger.hilt.android.plugin"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kapt = "kotlin-kapt"
    const val jvm = "jvm"
}

object AndroidBuildConfig {

    const val minSdk = 21
    const val compileSdk = 33
    const val targetSdk = compileSdk

    const val composeVersion = "1.1.1"

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Libraries {

    private object Versions {
        const val ktx = "1.7.0"
        const val ktxLifecycle = "2.3.1"
        const val composeActivity = "1.3.1"
        const val accompanist = "0.23.1"
        const val coroutines = "1.3.9"
        const val okHttp = "4.9.3"
        const val retrofit = "2.9.0"
        const val arrow = "0.11.0"
        const val landScapist = "2.0.2"
    }

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val ktxLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycle}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val gson = "com.google.code.gson:gson:${Versions.retrofit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val arrowSyntax = "io.arrow-kt:arrow-syntax:${Versions.arrow}"
    const val arrowMeta = "io.arrow-kt:arrow-meta:${Versions.arrow}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${BuildPlugins.Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${BuildPlugins.Versions.hilt}"
    const val hilt = "com.google.dagger:hilt-core:${BuildPlugins.Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${BuildPlugins.Versions.hilt}"

    const val composeUI = "androidx.compose.ui:ui:${AndroidBuildConfig.composeVersion}"
    const val accompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    const val composeMaterial = "androidx.compose.material:material:${AndroidBuildConfig.composeVersion}"
    const val composeUIToolingPreview = "androidx.compose.ui:ui-tooling-preview:${AndroidBuildConfig.composeVersion}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val composeUITooling = "androidx.compose.ui:ui-tooling:${AndroidBuildConfig.composeVersion}"
    const val landScapist = "com.github.skydoves:landscapist-glide:${Versions.landScapist}"

}

object TestLibraries {

    private object Versions {
        const val junit4 = "4.13.2"
        const val androidJunit = "1.1.3"
        const val espresso = "3.4.0"
        const val mockk = "1.12.1"
        const val turbine = "0.7.0"
        const val coroutineTest = "1.4.3"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val composeUIJunit4 = "androidx.compose.ui:ui-test-junit4:${AndroidBuildConfig.composeVersion}"
    const val hiltAndroid = "com.google.dagger:hilt-android-testing:${BuildPlugins.Versions.hilt}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkCommon = "io.mockk:mockk-common:${Versions.mockk}"

    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
}