plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.imagesearchapp.presentation"

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
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlin.Experimental",
        )
    }

    buildFeatures {
        // DataBinding Apply
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.recyclerView)
    implementation(libs.androidx.startup)

    // Android Material
    implementation(libs.android.material)

    // AndroidX Lifecycle
    implementation(libs.bundles.androidx.lifecycle)

    // AndroidX Paging
    implementation(libs.androidx.paging.runtime)
    // AndroidX Navigation
    implementation(libs.bundles.androidx.navigation)

    // Kotlinx Coroutines
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlin.stdlib)

    // Dagger2 ( DI )
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // Dagger2 ( DI ) Android Support
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation)
    kapt(libs.androidx.hilt.compiler)

    // ETC
    // Java 8 Desugaring
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    // Image loading library
    implementation(libs.glide)
    kapt(libs.glide)
    // Timber
    api(libs.timber)
    // Toasty
    implementation(libs.toasty)
    // Easy Permission
    implementation(libs.easyPermission)
}