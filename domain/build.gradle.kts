plugins {
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = Config.javaCompileTarget
    targetCompatibility = Config.javaCompileTarget
}

dependencies {
    // Kotlin
    implementation(Libraries.Kotlin.kotlin)
    implementation(Libraries.Kotlin.coroutine)

    // Paging
    implementation(Libraries.AndroidX.Paging.common)

    // Dagger2 ( DI )
    implementation(Libraries.Dagger.hilt)
    kapt(Libraries.Dagger.hiltCompiler)
}
