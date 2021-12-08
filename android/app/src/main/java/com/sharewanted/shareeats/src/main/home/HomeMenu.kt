package com.sharewanted.shareeats.src.main.home

import android.graphics.drawable.Drawable

data class HomeMenu (
    var image: Drawable,
    var title: String,
    var storeName: String,
    var currentPrice: Int,
    var minPrice: Int
        )