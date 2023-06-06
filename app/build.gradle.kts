import com.imagesearch.convention.ImageSearchConfig

plugins {
    id("imagesearch.android.application")
    id("imagesearch.android.hilt")
}

android {
    namespace = "com.example.imagesearchapp"

    defaultConfig {
        applicationId = ImageSearchConfig.applicationId
        minSdk = ImageSearchConfig.minSdk
        targetSdk = ImageSearchConfig.targetSdk
        compileSdk = ImageSearchConfig.compileSdk
        versionCode = ImageSearchConfig.versionCode
        versionName = ImageSearchConfig.versionName

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
}