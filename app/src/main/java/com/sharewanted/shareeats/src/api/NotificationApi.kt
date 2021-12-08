package com.sharewanted.shareeats.src.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApi {
    //notification API
    @GET("/subscribe/{subscribe}")
    fun sendMessage(@Path("subscribe") subscribe: String) : Call<String>

}