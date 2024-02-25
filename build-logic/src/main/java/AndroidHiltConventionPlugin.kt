import com.imagesearch.convention.Plugins
import com.imagesearch.convention.implementation
import com.imagesearch.convention.kapt
import com.imagesearch.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.Hilt)
                apply(Plugins.Kapt)
            }

            dependencies {
                implementation(libs.hilt.android)
                kapt(libs.hilt.android.compiler)
            }
        }
    }
}