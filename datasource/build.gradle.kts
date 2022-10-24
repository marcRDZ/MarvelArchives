plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.hilt)
    id(BuildPlugins.kapt)
}

android {
    compileSdk = AndroidBuildConfig.compileSdk

    defaultConfig {
        minSdk = AndroidBuildConfig.minSdk
        targetSdk = AndroidBuildConfig.targetSdk

        testInstrumentationRunner = AndroidBuildConfig.testRunner
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "MARVEL_BASE_URL", "\"https://gateway.marvel.com/\"")
        buildConfigField("String", "MARVEL_PUBLIC_API_KEY", "\"305ff550ebb4f1de55e9663f09eb9938\"")
        buildConfigField("String", "MARVEL_PRIVATE_API_KEY", "\"5cf1484470f30eae18efadde57afb1cea9b20034\"")
    }

    buildTypes {
        named("release").configure {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation(project(":core:data"))
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })

    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltAndroidCompiler)

    implementation(Libraries.ktxCore)
    implementation(Libraries.kotlinCoroutinesCore)

    implementation(Libraries.okHttp)
    implementation(Libraries.okHttpInterceptor)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConverter)

    implementation(Libraries.arrowCore)

    // For local unit tests
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.hiltAndroid)
    kaptTest(Libraries.hiltCompiler)

    // For instrumentation tests
    androidTestImplementation(TestLibraries.androidJunit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.hiltAndroid)
    kaptTest(Libraries.hiltCompiler)

}
