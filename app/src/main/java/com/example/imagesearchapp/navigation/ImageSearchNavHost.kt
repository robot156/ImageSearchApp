package com.example.imagesearchapp.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.imagesearchapp.presentation.screen.imagesearch.navigation.searchRoute
import com.example.imagesearchapp.presentation.screen.imagesearch.navigation.searchScreen
import com.example.imagesearchapp.presentation.screen.imagesearchlist.navigation.listScreen
import com.example.imagesearchapp.presentation.screen.imagesearchlist.navigation.navigateToList

@Composable
fun ImageSearchNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = searchRoute
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        searchScreen(onSearchClick = navController::navigateToList)

        listScreen(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth }
                )
            },
            onClickNavigationIcon = navController::navigateUp
        )
    }
}