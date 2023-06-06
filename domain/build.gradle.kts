plugins {
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = Config.javaCompileTarget
    targetCompatibility = Config.javaCompileTarget
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)

    // Paging
    implementation(libs.androidx.paging.common)

    // Dagger2 ( DI )
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}