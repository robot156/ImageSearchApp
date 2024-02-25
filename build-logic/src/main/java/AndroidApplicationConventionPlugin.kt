import com.android.build.api.dsl.ApplicationExtension
import com.imagesearch.convention.ImageSearchConfig
import com.imagesearch.convention.Plugins
import com.imagesearch.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.AndroidApplication)
                apply(Plugins.KotlinAndroid)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.apply {
                    applicationId = ImageSearchConfig.applicationId
                    targetSdk = ImageSearchConfig.targetSdk
                    versionCode = ImageSearchConfig.versionCode
                    versionName = ImageSearchConfig.versionName
                }
            }
        }
    }
}