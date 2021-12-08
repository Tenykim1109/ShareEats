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
        // Notification API Server
        const val NOTIFICATION_URL = "http://172.30.1.22:7777"

        // Firebase
        lateinit var databaseReference: DatabaseReference
        lateinit var storageRef: StorageReference

        lateinit var retrofit: Retrofit
        // notification 전송을 위한 retrofit
        lateinit var notiRetrofit: Retrofit
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

        notiRetrofit = Retrofit.Builder()
            .baseUrl(NOTIFICATION_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CreditCardRepository.initialize(this)
    }





}