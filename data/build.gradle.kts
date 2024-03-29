plugins {
    id("imagesearch.android.library")
    id("imagesearch.android.hilt")
    id("kotlinx-serialization")

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.imagesearchapp.data"

    defaultConfig {
        buildConfigField("String", "UNSPALSH_API_KEY", properties["UNSPALSH_API_KEY"] as String)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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