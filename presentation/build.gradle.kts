plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        compileSdk = Config.compileSdk

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile("proguard-rules.pro")
            buildConfigField("String", "UNSPALSH_API_KEY", properties["UNSPALSH_API_KEY"] as String)
        }
        debug {
            buildConfigField("String", "UNSPALSH_API_KEY", properties["UNSPALSH_API_KEY"] as String)
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Config.javaCompileTarget
        targetCompatibility = Config.javaCompileTarget
    }
    kotlinOptions {
        jvmTarget = Config.javaCompileTarget.toString()
    }

    buildFeatures {
        // DataBinding Apply
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    // AndroidX
    implementation(Libraries.AndroidX.core)
    implementation(Libraries.AndroidX.appcompat)
    implementation(Libraries.AndroidX.design)
    implementation(Libraries.AndroidX.constraint)
    implementation(Libraries.AndroidX.activity)
    implementation(Libraries.AndroidX.fragment)
    implementation(Libraries.AndroidX.startUp)

    //Android Lifecycle
    implementation(Libraries.AndroidX.Lifecycle.runtime)
    implementation(Libraries.AndroidX.Lifecycle.viewModel)
    implementation(Libraries.AndroidX.Lifecycle.savedState)
    implementation(Libraries.AndroidX.Lifecycle.service)
    kapt(Libraries.AndroidX.Lifecycle.compiler)
    // AndroidX Paging
    implementation(Libraries.AndroidX.Paging.runtime)
    // AndroidX Navigation
    implementation(Libraries.AndroidX.Navigation.ui)
    implementation(Libraries.AndroidX.Navigation.fragment)

    // Kotlin
    implementation(Libraries.Kotlin.kotlin)
    implementation(Libraries.Kotlin.coroutine)

    // Dagger2 ( DI )
    implementation(Libraries.Dagger.androidHilt)
    kapt(Libraries.Dagger.androidHiltCompiler)
    // Dagger2 ( DI ) Android Support
    implementation(Libraries.AndroidX.Hilt.common)
    implementation(Libraries.AndroidX.Hilt.navigation)
    kapt(Libraries.AndroidX.Hilt.compiler)

    // ETC
    // Java 8 Desugaring
    coreLibraryDesugaring(Libraries.desugar)
    // Image loading library
    implementation(Libraries.Glide.core)
    kapt(Libraries.Glide.compiler)
    // Timber
    api(Libraries.timber)
    // Toasty
    implementation(Libraries.toasty)
    // Easy Permission
    implementation(Libraries.permission)
}