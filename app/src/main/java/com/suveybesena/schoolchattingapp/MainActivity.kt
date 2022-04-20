package com.suveybesena.schoolchattingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.teachersFragment, R.id.messagesFragment, R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.loginFragment) {
                bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.teacherRegisterFragment) {
                bottomNavigationView.visibility = View.GONE
            }  else if (destination.id == R.id.guidanceFragment) {
                bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.studentRegisterFragment) {
                bottomNavigationView.visibility = View.GONE
            }
            else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}