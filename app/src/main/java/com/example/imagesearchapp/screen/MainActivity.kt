package com.example.imagesearchapp.screen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.DataBindingActivity
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val navController : NavController?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)?.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController?.setGraph(R.navigation.nav_graph)
    }

    override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false
}