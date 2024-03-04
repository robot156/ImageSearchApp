package com.example.imagesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.imagesearchapp.designsystem.theme.ImageSearchTheme
import com.example.imagesearchapp.navigation.ImageSearchNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageSearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ImageSearchTheme {
                ImageSearchNavHost()
            }
        }
    }
}