package com.sharewanted.shareeats.src.main.home.order.selectLocation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GeocodingApi {

    @Headers(
        "X-NCP-APIGW-API-KEY-ID: qg2dq62e1v",
        "X-NCP-APIGW-API-KEY: 7ZjCUUbEHaK2ABuSYCj2dhUgJ6GwjbJJhl30nqEL"
    )
    @GET("map-reversegeocode/v2/gc")
    fun getAddress(@Query("coords") coords: String, @Query("orders") orders: String, @Query("output") output: String) : Call<AddressResponse>

    @Headers(
        "X-NCP-APIGW-API-KEY-ID: qg2dq62e1v",
        "X-NCP-APIGW-API-KEY: 7ZjCUUbEHaK2ABuSYCj2dhUgJ6GwjbJJhl30nqEL"
    )
    @GET("map-geocode/v2/geocode")
    fun getCoords(@Query("query") location: String) : Call<CoordsResponse>
}