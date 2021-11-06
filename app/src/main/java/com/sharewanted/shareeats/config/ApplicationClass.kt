package com.sharewanted.shareeats.config

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import com.sharewanted.shareeats.database.creditcard.CreditCardRepository

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("qg2dq62e1v")

        CreditCardRepository.initialize(this)
    }
}