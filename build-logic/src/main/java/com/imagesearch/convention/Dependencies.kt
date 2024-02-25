package com.imagesearch.convention

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? {
    return add("implementation", dependencyNotation)
}

internal fun DependencyHandler.ksp(dependencyNotation: Any): Dependency? {
    return add("ksp", dependencyNotation)
}

internal fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? {
    return add("kapt", dependencyNotation)
}

internal fun DependencyHandler.coreLibraryDesugaring(dependencyNotation: Any): Dependency? {
    return add("coreLibraryDesugaring", dependencyNotation)
}