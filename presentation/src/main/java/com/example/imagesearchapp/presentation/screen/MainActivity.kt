package com.example.imagesearchapp.presentation.screen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val navController : NavController?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController?.setGraph(R.navigation.nav_image_search_main_graph)
    }

    override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false
}