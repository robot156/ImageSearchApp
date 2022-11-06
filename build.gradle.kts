buildscript {

    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Libraries.GradlePlugin.android)
        classpath(Libraries.GradlePlugin.kotlin)
        classpath(Libraries.GradlePlugin.kotlinSerializable)
        classpath(Libraries.GradlePlugin.daggerHilt)
        classpath(Libraries.GradlePlugin.safeArgs)
    }
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}