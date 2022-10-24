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
        vectorDrawables {
            useSupportLibrary = true
        }
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AndroidBuildConfig.composeVersion
    }
}

dependencies {

    implementation(project(":core:presentation"))

    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltAndroidCompiler)

    implementation(Libraries.ktxCore)
    implementation(Libraries.composeUI)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.accompanistSwipeRefresh)
    implementation(Libraries.composeUIToolingPreview)
    implementation(Libraries.ktxLifecycle)
    implementation(Libraries.composeActivity)
    implementation(Libraries.landScapist)


    // For local unit tests
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.hiltAndroid)
    kaptTest(Libraries.hiltCompiler)

    // For instrumentation tests
    androidTestImplementation(TestLibraries.androidJunit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.composeUIJunit4)
    androidTestImplementation(TestLibraries.hiltAndroid)
    kaptTest(Libraries.hiltCompiler)
    debugImplementation(Libraries.composeUITooling)
}