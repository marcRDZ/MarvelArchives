plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.hilt)
    id(BuildPlugins.kapt)
}

android {
    compileSdk = AndroidBuildConfig.compileSdk

    defaultConfig {
        applicationId = "es.marcrdz.marvel_archives"
        minSdk = AndroidBuildConfig.minSdk
        targetSdk = AndroidBuildConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

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

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {

    implementation(project(":ui"))
    implementation(project(":datasource"))

    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltAndroidCompiler)

    implementation(Libraries.ktxCore)
    implementation(Libraries.ktxLifecycle)

    implementation(Libraries.composeUI)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.composeUIToolingPreview)
    implementation(Libraries.composeActivity)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.androidJunit)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.composeUIJunit4)
    debugImplementation(Libraries.composeUITooling)

}

kapt {
    correctErrorTypes = true
}