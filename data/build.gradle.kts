plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        compileSdk = Config.compileSdk
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
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Config.javaCompileTarget
        targetCompatibility = Config.javaCompileTarget
    }
    kotlinOptions {
        jvmTarget = Config.javaCompileTarget.toString()
    }
}

dependencies {
    implementation(project(":domain"))

    // AndroidX Room
    implementation(Libraries.AndroidX.Room.runtime)
    implementation(Libraries.AndroidX.Room.coroutine)
    implementation(Libraries.AndroidX.Room.paging)
    kapt(Libraries.AndroidX.Room.compiler)

    // Retrofit2
    implementation(Libraries.Retrofit2.core)
    implementation(Libraries.Retrofit2.converterKotlin)
    // logging interceptor
    implementation(Libraries.OkHttp.logger)
    // Kotlin
    implementation(Libraries.Kotlin.kotlin)
    implementation(Libraries.Kotlin.kotlinSerializable)
    implementation(Libraries.Kotlin.coroutine)

    // Dagger2 ( DI )
    implementation(Libraries.Dagger.androidHilt)
    kapt(Libraries.Dagger.androidHiltCompiler)

    // ETC
    coreLibraryDesugaring(Libraries.desugar)
    api(Libraries.timber)
}