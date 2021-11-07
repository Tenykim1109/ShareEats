package com.sharewanted.shareeats.src.main.home.order.findRestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityFindRestaurantBinding

class FindRestaurantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFindRestaurantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}