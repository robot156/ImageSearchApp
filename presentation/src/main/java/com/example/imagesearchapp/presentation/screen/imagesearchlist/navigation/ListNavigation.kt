package com.example.imagesearchapp.presentation.screen.imagesearchlist.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imagesearchapp.presentation.screen.imagesearchlist.ImageSearchListRoute

const val listRoute = "list_route"
internal const val listQueryArg = "query"

fun NavController.navigateToList(query: String) {
    this.navigate(listRoute.plus("/").plus(query)) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.listScreen(
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    onClickNavigationIcon: () -> Unit
) {
    composable(
        route = listRoute.plus("/").plus("{$listQueryArg}"),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = enterTransition,
        popExitTransition = exitTransition,
        arguments = listOf(
            navArgument(listQueryArg) { type = NavType.StringType },
        )
    ) {
        ImageSearchListRoute(
            onClickNavigationIcon = onClickNavigationIcon
        )
    }
}