plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        compileSdk = Config.compileSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Dagger2 ( DI )
    implementation(Libraries.Dagger.androidHilt)
    kapt(Libraries.Dagger.androidHiltCompiler)
    // Dagger2 ( DI ) Android Support
    implementation(Libraries.AndroidX.Hilt.common)
    kapt(Libraries.AndroidX.Hilt.compiler)
    /** etc */
    // CoreLibrary Desugar
    coreLibraryDesugaring(Libraries.desugar)
    // Toasty
    implementation(Libraries.toasty)

}