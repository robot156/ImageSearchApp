plugins {
    id("imagesearch.android.application")
    id("imagesearch.android.application.compose")
    id("imagesearch.android.hilt")
}

android {
    namespace = "com.example.imagesearchapp"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":presentation:designsystem"))
    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX Activity
    implementation(libs.androidx.activity.compose)

    // AndroidX Compose
    implementation(libs.androidx.compose.runtime)

    // AndroidX Navigation
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    // AndroidX Startup
    implementation(libs.androidx.startup)

    // Coil
    implementation(libs.coil.core)
}