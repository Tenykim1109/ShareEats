package com.sharewanted.shareeats.src.main.home.order.selectLocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.ActivitySelectLocationBinding
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.EditText
import com.sharewanted.shareeats.src.api.GeocodeApi
import com.sharewanted.shareeats.src.main.location.model.Geocode

class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivitySelectLocationBinding
    private lateinit var mapView: MapView
    private var naverMap: NaverMap? = null
    private var locationSource: FusedLocationSource? = null
    private var marker = Marker()
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activitySelectLocationToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        mapView = binding.activitySelectLocationNaverMap
        mapView.getMapAsync(this)

        binding.activitySelectLocationBtnComplete.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        binding.activitySelectLocationBtnSearch.setOnClickListener {
            val geocodingService = ApplicationClass.retrofit.create(GeocodeApi::class.java)
            geocodingService.getGeocode(binding.activitySelectLocationEtAddress.text.toString()).enqueue(object : Callback<Geocode> {
                override fun onResponse(call: Call<Geocode>, response: Response<Geocode>) {
                    if (response.isSuccessful) {
                        val geocode = response.body() as Geocode

                        Log.d("coords test", geocode.toString())

                        if (geocode.addresses.size != 0) {
                            val lat = geocode.addresses[0].x
                            val lng = geocode.addresses[0].y

                            setMarker(lat, lng)

                            registerLocation(binding.activitySelectLocationEtAddress.text.toString())
                        }


                    }
                }

                override fun onFailure(call: Call<Geocode>, t: Throwable) {
                    Log.d("getGeocode error", t.toString())
                    Toast.makeText(this@SelectLocationActivity, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationSource!!.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        naverMap!!.locationSource = locationSource
        naverMap!!.locationTrackingMode = LocationTrackingMode.Follow
        naverMap!!.uiSettings.isLocationButtonEnabled = true

        naverMap!!.setOnMapClickListener { pointF, latLng ->
            val lng = latLng.longitude
            val lat = latLng.latitude

            searchAddress(lng, lat)
        }

    }


    fun searchAddress(lat: Double, lng: Double) {

        marker.position = LatLng(lng, lat)
        marker.map = naverMap

        val reverseGeocodingService = ApplicationClass.retrofit.create(GeocodeApi::class.java)

        val latLng = LatLngCoords(lat, lng).toString()

        Log.d("coords check", latLng)

        reverseGeocodingService.getAddress(latLng, "legalcode,addr", "json").enqueue(object : Callback<AddressResponse>{
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {

                if (response.isSuccessful) {

                    val addressResponse = response.body() as AddressResponse

                    var address = ""

                    address += "${addressResponse.results[1].region.area1.name} "
                    address += "${addressResponse.results[1].region.area2.name} "
                    address += "${addressResponse.results[1].region.area3.name} "
                    address += "${addressResponse.results[1].region.area4.name} "
                    address += "${addressResponse.results[1].land.number1}"
                    if (addressResponse.results[1].land.number2 != "") {
                        address += "-${addressResponse.results[1].land.number2}"
                    }

                    Log.d("address check", address)

                    val searchView = findViewById<EditText>(R.id.activity_select_location_et_address)
                    searchView.setText(address)

                    registerLocation(address)

                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                Log.d("getAddress error", t.toString())
                Toast.makeText(this@SelectLocationActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })


    }


    fun setMarker(lat: Double, lng: Double) {
        Log.d("setMarker check", "${lat} ${lng}")
        marker.position = LatLng(lng, lat)
        marker.map = naverMap

        naverMap!!.moveCamera(CameraUpdate.scrollTo(LatLng(lng, lat)))
    }


    fun registerLocation(address: String) {
        binding.activitySelectLocationBtnComplete.setOnClickListener {
            val intent = Intent(this@SelectLocationActivity, OrderActivity::class.java).apply {
                putExtra("resultType", "selectLocation")
                putExtra("location", address)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }


}