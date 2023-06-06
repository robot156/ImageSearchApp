plugins {
    id("imagesearch.jvm.library")
    id("imagesearch.jvm.hilt")
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)

    // Paging
    implementation(libs.androidx.paging.common)
}