package com.sharewanted.shareeats.src.main.location

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.FragmentLocationBinding


class LocationFragment : Fragment(), OnMapReadyCallback, TextView.OnEditorActionListener {

    private val TAG = "LocationFragment"
    private val PERMISSION_REQUEST_CODE = 100

    private lateinit var binding: FragmentLocationBinding
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var mapFragment: MapFragment
    private lateinit var search: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search = view.findViewById(R.id.editTextTextPersonName)
        search.setOnEditorActionListener(this)

        // 네이버 지도 초기화
        initMap()

        // 현재 위치를 반환하는 구현체
        mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)
    }

    /* TedPermission 사용하여 위치 권한 취득 */
    private fun checkPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Log.d(TAG, "위치 권한 확인.")

                // 현재 위치 추적
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(requireContext(), "위치 권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission, you can not use this service\\n\\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

    private fun initMap() {
        val fm = childFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        // 비동기로 onMapReady 콜백 함수 호출, onMapReady에서 NaverMap 객체 초기화
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(map: NaverMap) {
        Log.d(TAG, "onMapReady")

        // NaverMap 객체를 받아와 객체에 위치 소스 지정
        naverMap = map
        naverMap.locationSource = mLocationSource

        // UI Controls
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true // 나침반 아이콘
        uiSettings.isScaleBarEnabled = true // 축척바
        uiSettings.isZoomControlEnabled = true // 지도 줌 버튼
        uiSettings.isLocationButtonEnabled = true // 현재위치 버튼


        // 위치 권한 확인
        checkPermission()
    }

    override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (tv!!.id == R.id.editTextTextPersonName && actionId == EditorInfo.IME_ACTION_DONE) {
            Log.d(TAG, binding.editTextTextPersonName.text.toString())
            search.text = ""
        }
        return false
    }
}