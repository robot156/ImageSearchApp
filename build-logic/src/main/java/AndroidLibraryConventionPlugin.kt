import com.android.build.gradle.LibraryExtension
import com.imagesearch.convention.ImageSearchConfig
import com.imagesearch.convention.Plugins
import com.imagesearch.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.AndroidLibrary)
                apply(Plugins.KotlinAndroid)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ImageSearchConfig.targetSdk
            }
        }
    }
}