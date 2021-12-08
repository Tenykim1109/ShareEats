package com.sharewanted.shareeats.config

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naver.maps.map.NaverMapSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sharewanted.shareeats.util.SharedPreferencesUtil
import com.sharewanted.shareeats.database.creditcard.CreditCardRepository

class ApplicationClass : Application() {

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil

        // Geocode API URL
        const val NAVER_GEOCODE_URL = "https://naveropenapi.apigw.ntruss.com"

        // Firebase
        lateinit var databaseReference: DatabaseReference
        lateinit var storageRef: StorageReference

        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("qg2dq62e1v")

        //shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        // Firebase 초기화
        databaseReference = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        // Retrofit 초기화
        retrofit = Retrofit.Builder()
            .baseUrl(NAVER_GEOCODE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CreditCardRepository.initialize(this)
    }





}