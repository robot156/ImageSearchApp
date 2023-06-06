plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.imagesearchapp"

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        compileSdk = Config.compileSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = Config.javaCompileTarget
        targetCompatibility = Config.javaCompileTarget
    }
    kotlinOptions {
        jvmTarget = Config.javaCompileTarget.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.androidx.startup)

    // Dagger2 ( DI )
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.common)
    kapt(libs.androidx.hilt.compiler)
}