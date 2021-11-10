package com.sharewanted.shareeats.src.api

import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.location.model.Geocode
import retrofit2.Call
import retrofit2.http.*

interface GeocodeApi {

    @Headers("X-NCP-APIGW-API-KEY-ID: qg2dq62e1v", "X-NCP-APIGW-API-KEY: 7ZjCUUbEHaK2ABuSYCj2dhUgJ6GwjbJJhl30nqEL")
    @GET("map-geocode/v2/geocode")
    fun getGeocode(@Query("query") query: String): Call<Geocode>
}