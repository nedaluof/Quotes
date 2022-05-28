package com.nedaluof.quotes.ui.main

import android.os.Bundle
import androidx.collection.arraySetOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivityMainBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingVariable = 0
    override val layoutId = R.layout.activity_main
    override fun getViewModel(): BaseViewModel? = null
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**TODO: use dataBinding here**/
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            arraySetOf(
                R.id.navigation_anime, R.id.navigation_inspirational, R.id.navigation_motivational
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp() || super.onSupportNavigateUp()
}