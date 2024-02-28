plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "imagesearch.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "imagesearch.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "imagesearch.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "imagesearch.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidHilt") {
            id = "imagesearch.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("jvmLibrary") {
            id = "imagesearch.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }

        register("jvmHilt") {
            id = "imagesearch.jvm.hilt"
            implementationClass = "JvmHiltConventionPlugin"
        }
    }
}