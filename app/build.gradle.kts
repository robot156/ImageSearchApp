import com.imagesearch.convention.ImageSearchConfig

plugins {
    id("imagesearch.android.application")
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
    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.androidx.startup)
}