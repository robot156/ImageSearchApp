object Libraries {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Versions.GradlePlugin.android}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.kotlin}"
        const val kotlinSerializable ="org.jetbrains.kotlin:kotlin-serialization:${Versions.Kotlin.kotlin}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Dagger2.hilt}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.navigation}"
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.kotlin}"
        const val kotlinSerializable = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.kotlinSerializable}"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutine}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraint}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        const val design = "com.google.android.material:material:${Versions.AndroidX.design}"
        const val activity = "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
        const val startUp = "androidx.startup:startup-runtime:${Versions.AndroidX.startUp}"

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.AndroidX.lifecycle}"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
            const val service = "androidx.lifecycle:lifecycle-service:${Versions.AndroidX.lifecycle}"
            const val process = "androidx.lifecycle:lifecycle-process:${Versions.AndroidX.lifecycle}"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.lifecycle}"// For Kotlin use kapt instead of annotationProcessor
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
            const val coroutine = "androidx.room:room-ktx:${Versions.AndroidX.room}"
            const val paging = "androidx.room:room-paging:${Versions.AndroidX.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"  // For Kotlin use kapt instead of annotationProcessor
        }

        object Navigation {
            const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime-ktx:${Versions.AndroidX.paging}"
            const val common = "androidx.paging:paging-common-ktx:${Versions.AndroidX.paging}"
        }

        object Hilt {
            const val common = "androidx.hilt:hilt-common:${Versions.AndroidX.hilt}"
            const val navigation = "androidx.hilt:hilt-navigation-fragment:${Versions.AndroidX.hilt}"
            const val compiler = "androidx.hilt:hilt-compiler:${Versions.AndroidX.hilt}" // For Kotlin use kapt instead of annotationProcessor
        }
    }

    object Retrofit2 {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
        const val converterKotlin = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitConverter}"
    }

    object OkHttp {
        const val logger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    }

    object Dagger {
        const val hilt = "com.google.dagger:hilt-core:${Versions.Dagger2.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.Dagger2.hilt}" // For Kotlin use kapt instead of annotationProcessor

        const val androidHilt = "com.google.dagger:hilt-android:${Versions.Dagger2.hilt}"
        const val androidHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Dagger2.hilt}" // For Kotlin use kapt instead of annotationProcessor
    }

    object Glide {
        const val core = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"  // For Kotlin use kapt instead of annotationProcessor
    }

    const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}" // coreLibraryDesugaring
    const val toasty = "com.github.GrenderG:Toasty:1.5.2"
    const val permission = "pub.devrel:easypermissions:3.0.0"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}