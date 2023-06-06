import com.imagesearch.convention.ImageSearchConfig

plugins {
    id("imagesearch.android.library")
    id("imagesearch.android.hilt")
    id("kotlinx-serialization")

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.imagesearchapp.data"

    defaultConfig {
        minSdk = ImageSearchConfig.minSdk
        targetSdk = ImageSearchConfig.targetSdk
        compileSdk = ImageSearchConfig.compileSdk
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
}

dependencies {
    implementation(project(":domain"))

    // AndroidX Room
    implementation(libs.bundles.androidx.room)
    ksp(libs.androidx.room.compiler)

    // Retrofit2
    implementation(libs.bundles.retrofit)

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    // ETC
    api(libs.timber)
}