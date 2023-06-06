plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.imagesearchapp.data"

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        compileSdk = Config.compileSdk
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
}

dependencies {
    implementation(project(":domain"))

    // AndroidX Room
    implementation(libs.bundles.androidx.room)
    kapt(libs.androidx.room.compiler)

    // Retrofit2
    implementation(libs.bundles.retrofit)

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    // Dagger2 ( DI )
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // ETC
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    api(libs.timber)
}