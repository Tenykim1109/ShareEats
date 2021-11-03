package com.sharewanted.shareeats.src.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.sharewanted.shareeats.databinding.ActivityMainBinding
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.chat.ChatListFragment
import com.sharewanted.shareeats.src.main.home.HomeFragment
import com.sharewanted.shareeats.src.main.location.LocationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigation : BottomNavigationView
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, ChatListFragment())
            .commit()

        bottomNavigation = binding.bottomNavigationView

        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host, HomeFragment())
                        .commit()
                    true
                }
                R.id.chatFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host, ChatListFragment())
                        .commit()
                    true
                }
                R.id.locationFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host, LocationFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}