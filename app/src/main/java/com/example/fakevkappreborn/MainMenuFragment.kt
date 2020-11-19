package com.example.fakevkappreborn

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        /*var selectedFragment: Fragment = NewsFragment()


        childFragmentManager.beginTransaction().replace(
            R.id.fragment_menu_container,
            selectedFragment
        ).commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> selectedFragment = NewsFragment()
                R.id.nav_messages -> selectedFragment = MessagesFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }
            childFragmentManager.beginTransaction().replace(
                R.id.fragment_menu_container,
                selectedFragment
            ).commit()
            return@setOnNavigationItemSelectedListener true
        }*/
    }

    fun setupNavigation() {
        val act = activity
        Log.d("MyActivityLog", "$act")
        val navHostFragment =
            act?.supportFragmentManager?.findFragmentById(R.id.fragment_menu_container) as NavHostFragment

        NavigationUI.setupWithNavController(bottom_navigation, navHostFragment.navController)
    }
}