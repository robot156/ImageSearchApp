plugins {
    id("imagesearch.android.library")
    id("imagesearch.android.hilt")
    id("imagesearch.android.library.compose")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.imagesearchapp.presentation"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        // DataBinding Apply
        dataBinding = true
    }
}

dependencies {
    implementation(project(":presentation:designsystem"))
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.recyclerView)
    implementation(libs.androidx.startup)

    // AndroidX Compose
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    // AndroidX Activity
    implementation(libs.androidx.activity.compose)

    // Android Material
    implementation(libs.android.material)

    // AndroidX Lifecycle
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.lifecycle.compose)

    // AndroidX Paging
    implementation(libs.androidx.paging.runtime)
    // AndroidX Navigation
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.navigation.compose)

    // Kotlinx Coroutines
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlin.stdlib)

    // Dagger2 ( DI ) Android Support
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.hilt.navigation.compose)

    // Toasty
    implementation(libs.toasty)
    // Easy Permission
    implementation(libs.easyPermission)

    // Image loading library
    implementation(libs.glide)
    ksp(libs.glide.compiler)
    // Timber
    api(libs.timber)
}