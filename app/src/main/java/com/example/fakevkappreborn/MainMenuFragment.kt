package com.example.fakevkappreborn

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fakevkappreborn.menufragments.MessagesFragment
import com.example.fakevkappreborn.menufragments.NewsFragment
import com.example.fakevkappreborn.menufragments.ProfileFragment
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment(val setFragment: Int = 10) : Fragment(R.layout.fragment_main_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedFragment: Fragment = NewsFragment()

        Log.d("MySetFragmentLog", "On View Created $setFragment")

        fragmentManager?.beginTransaction()?.replace(R.id.fragment_menu_container,
            selectedFragment)?.commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_news -> selectedFragment = NewsFragment()
                R.id.nav_messages -> selectedFragment = MessagesFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_menu_container,
                selectedFragment)?.commit()
            return@setOnNavigationItemSelectedListener true
        }
    }
}