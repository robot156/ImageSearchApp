plugins {
    id("imagesearch.android.library")
    id("imagesearch.android.library.compose")
}

android {
    namespace = "com.example.imagesearchapp.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.accompanist.systemuicontroller)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
}