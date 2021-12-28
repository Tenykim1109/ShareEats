package com.sharewanted.shareeats.src.api

import com.sharewanted.shareeats.src.main.home.order.selectLocation.AddressResponse
import com.sharewanted.shareeats.src.main.location.model.Geocode
import retrofit2.Call
import retrofit2.http.*

interface GeocodeApi {

    @Headers("X-NCP-APIGW-API-KEY-ID: xxxxxxxxxx", "X-NCP-APIGW-API-KEY: XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    @GET("/map-geocode/v2/geocode")
    fun getGeocode(@Query("query") query: String): Call<Geocode>

    @Headers("X-NCP-APIGW-API-KEY-ID: xxxxxxxxxx", "X-NCP-APIGW-API-KEY: XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    @GET("map-reversegeocode/v2/gc")
    fun getAddress(@Query("coords") coords: String, @Query("orders") orders: String, @Query("output") output: String) : Call<AddressResponse>
}