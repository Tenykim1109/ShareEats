package com.sharewanted.shareeats.service

import android.util.Log
import com.sharewanted.shareeats.src.main.location.model.Geocode
import com.sharewanted.shareeats.util.RetrofitCallback
import com.sharewanted.shareeats.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeocodeService {
    private val TAG = "GeocodeService"

    fun getGeocode(query: String, coordinate: String, callback: RetrofitCallback<Boolean>) {
        RetrofitUtil.geocodeService.getGeocode(query, coordinate).enqueue(object : Callback<Geocode> {

            override fun onResponse(call: Call<Geocode>, response: Response<Geocode>) {
                if (response.code() == 200) {
                    val res = response.body() as Geocode
                    Log.d(TAG, "getGeocode: $res")
                } else {
                    Log.d(TAG, "getGeocode: Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Geocode>, t: Throwable) {
                Log.e(TAG, "Failed")
            }
        })
    }


}