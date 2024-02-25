plugins {
    id("imagesearch.android.library")
    id("imagesearch.android.hilt")
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

    // Dagger2 ( DI ) Android Support
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation)

    // Image loading library
    implementation(libs.glide)
    ksp(libs.glide.compiler)
    // Timber
    api(libs.timber)
    // Toasty
    implementation(libs.toasty)
    // Easy Permission
    implementation(libs.easyPermission)
}