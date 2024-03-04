package com.example.imagesearchapp.presentation.screen.imagesearch.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.imagesearchapp.presentation.screen.imagesearch.ImageSearchRoute

const val searchRoute = "search_route"

fun NavGraphBuilder.searchScreen(
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    onSearchClick: (String) -> Unit
) {
    composable(
        route = searchRoute,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        ImageSearchRoute(onSearchClick = onSearchClick)
    }
}