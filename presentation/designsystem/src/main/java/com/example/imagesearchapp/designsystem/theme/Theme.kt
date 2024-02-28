package com.example.imagesearchapp.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_on_primary,
    primaryContainer = md_theme_light_primary_container,
    secondary = md_theme_light_secondary,
    secondaryContainer = md_theme_light_secondary_container,
    onSecondary = md_theme_light_on_secondary,
    onSurface = md_theme_light_on_surface,
    onBackground = md_theme_light_onBackground,
    outline = md_theme_light_primary_container,
)

private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_on_primary,
    primaryContainer = md_theme_dark_primary_container,
    secondary = md_theme_dark_secondary,
    secondaryContainer = md_theme_dark_secondary_container,
    onSecondary = md_theme_dark_on_secondary,
    onSurface = md_theme_dark_on_surface,
    onBackground = md_theme_dark_onBackground,
    outline = md_theme_dark_primary_container,
)

@Composable
fun ImageSearchTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) LightColors else DarkColors
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(systemUiController, useDarkTheme) {
        systemUiController.setSystemBarsColor(
            color = colors.surface,
            darkIcons = !useDarkTheme
        )
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}