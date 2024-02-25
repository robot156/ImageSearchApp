import com.imagesearch.convention.Plugins
import com.imagesearch.convention.implementation
import com.imagesearch.convention.ksp
import com.imagesearch.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class JvmHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.Ksp)
            }

            dependencies {
                implementation(libs.hilt.core)
                ksp(libs.hilt.compiler)
            }
        }
    }
}