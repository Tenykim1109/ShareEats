package com.sharewanted.shareeats.util

import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.src.api.GeocodeApi

class RetrofitUtil {
    companion object {
        val geocodeService = ApplicationClass.retrofit.create(GeocodeApi::class.java)
    }
}
        