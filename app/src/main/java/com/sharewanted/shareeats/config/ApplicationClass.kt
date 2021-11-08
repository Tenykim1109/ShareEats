package com.sharewanted.shareeats.config

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naver.maps.map.NaverMapSdk
import com.sharewanted.shareeats.util.SharedPreferencesUtil
import com.sharewanted.shareeats.database.creditcard.CreditCardRepository

class ApplicationClass : Application() {
    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil

        // Firebase
        lateinit var databaseReference: DatabaseReference
        lateinit var storageRef: StorageReference
    }

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("qg2dq62e1v")

        //shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        // Firebase 초기화
        databaseReference = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        CreditCardRepository.initialize(this)
    }
}