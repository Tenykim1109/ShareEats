package com.sharewanted.shareeats.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sharewanted.shareeats.src.main.location.model.Geocode
import com.sharewanted.shareeats.util.RetrofitCallback
import com.sharewanted.shareeats.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeocodeService {
    private val TAG = "GeocodeService"

    fun getGeocode(query: String, callback: RetrofitCallback<Boolean>): MutableLiveData<Geocode> {
        val responseLiveData: MutableLiveData<Geocode> = MutableLiveData()

        RetrofitUtil.geocodeService.getGeocode(query).enqueue(object : Callback<Geocode> {
            override fun onResponse(call: Call<Geocode>, response: Response<Geocode>) {
                if (response.code() == 200) {
                    responseLiveData.value = response.body() as Geocode
                } else {
                    Log.d(TAG, "getGeocode: Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Geocode>, t: Throwable) {
                Log.e(TAG, "Failed")
            }
        })

        return responseLiveData
    }
}