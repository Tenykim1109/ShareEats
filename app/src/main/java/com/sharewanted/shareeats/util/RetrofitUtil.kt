package com.sharewanted.shareeats.util

import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.src.main.home.order.selectLocation.GeocodingApi

class RetrofitUtil {
    companion object {
        val geocodingApi = ApplicationClass.retrofit.create(GeocodingApi::class.java)
    }
}