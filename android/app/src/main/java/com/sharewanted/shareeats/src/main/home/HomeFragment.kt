package com.sharewanted.shareeats.src.main.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.FragmentHomeBinding
import com.sharewanted.shareeats.src.api.GeocodingApi
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.home.fragment.*
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.selectLocation.AddressResponse
import com.sharewanted.shareeats.src.main.home.postInfo.PostInfoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HomeFragment_싸피"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var postList = mutableListOf<Post>()
    private val mDatabase = Firebase.database.reference
    private lateinit var mainActivity: MainActivity
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentTab: TabLayout.Tab? = null
    private var check = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLocationRequest =  LocationRequest.create().apply {
            interval = 2000 // 업데이트 간격 단위(밀리초)
            fastestInterval = 1000 // 가장 빠른 업데이트 간격 단위(밀리초)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
            maxWaitTime= 2000 // 위치 갱신 요청 최대 대기 시간 (밀리초)
        }

        startLocationUpdates()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        initView()
    }

    private fun currentLocation() {

        ApplicationClass.retrofit.create(GeocodingApi::class.java)
            .getAddress("${mLastLocation.longitude},${mLastLocation.latitude}", "legalcode,addr", "json")
            .enqueue(object : Callback<AddressResponse> {
                override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                    if (response.isSuccessful) {

                        val addressResponse = response.body() as AddressResponse

                        var dongName = ""

                        dongName += "${addressResponse.results[1].region.area3.name}"

                        binding.fragmentHomeTvDongName.text = dongName

                        childFragmentManager.beginTransaction()
                            .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }

                override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
    }

    private fun initView() {
        
        mDatabase.child("Post").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (dataSnapshot in snapshot.children) {
                    val completed = dataSnapshot.child("completed").getValue(String::class.java).toString()

                    if (completed == "모집중") {
                        val post = dataSnapshot.getValue(Post::class.java)
                        postList.add(post!!)
                    }
                }

                val searchTitleAdapter = SearchTitleAdapter(mainActivity, R.layout.fragment_home_list_item_search_title, postList)
                binding.fragmentHomeActvSearchTitle.setAdapter(searchTitleAdapter)
                binding.fragmentHomeActvSearchTitle.setOnItemClickListener { adapterView, view, i, l ->
                    val selectedPost = postList[i]
                    val intent = Intent(mainActivity, PostInfoActivity::class.java).apply {
                        putExtra("postId", selectedPost.postId)
                    }
                    startActivity(intent)
                    binding.fragmentHomeActvSearchTitle.setText("")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Post error", error.toString())
                Toast.makeText(mainActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        binding.fragmentHomeActvSearchTitle.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.fragmentHomeActvSearchTitle.windowToken, 0)
                true
            }
            false
        }


        binding.fbWritePost.setOnClickListener {
            val intent = Intent(mainActivity, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.fragmentHomeTlMenuType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab
                loadListFragment(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                loadListFragment(tab)
            }

        })


        binding.fragmentHomeTvDongName.setOnClickListener {

            var dongName = ""

            val dialog = AlertDialog.Builder(mainActivity)
                .setView(R.layout.dialog_select_dong)
                .setPositiveButton("확인") { dialog, which ->
                    if (dongName != "") {
                        binding.fragmentHomeTvDongName.text = dongName
                        if (currentTab == null) {
                            childFragmentManager.beginTransaction()
                                .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit()
                        } else {
                            loadListFragment(currentTab)
                        }
                        Toast.makeText(mainActivity, "경상북도 구미시 ${binding.fragmentHomeTvDongName.text}", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }.create()
            dialog.show()

            val spinner = dialog.findViewById<Spinner>(R.id.dialog_select_dong_spinner)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>((activity as AppCompatActivity).applicationContext, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.location_array))
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    dongName = spinner.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(mainActivity, "선택안함", Toast.LENGTH_SHORT).show()
                }

            }

            val btnCurrentLocation = dialog.findViewById<Button>(R.id.dialog_select_dong_btn_current_location)

            btnCurrentLocation.setOnClickListener {

                Log.d(TAG, "initView: ${mLastLocation.latitude}, ${mLastLocation.longitude}")

                ApplicationClass.retrofit.create(GeocodingApi::class.java).getAddress("${mLastLocation.longitude},${mLastLocation.latitude}", "legalcode,addr", "json")
                    .enqueue(object : Callback<AddressResponse> {
                        override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {

                            Log.d(TAG, "onResponse: ${response.body()}")
                            if (response.isSuccessful) {

                                val addressResponse = response.body() as AddressResponse

                                var dongName = ""

                                dongName += "${addressResponse.results[1].region.area3.name}"

                                binding.fragmentHomeTvDongName.text = dongName
                                Toast.makeText(mainActivity, "경상북도 구미시 ${dongName}", Toast.LENGTH_SHORT).show()
                                if (currentTab == null) {
                                    childFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .commit()
                                } else {
                                    loadListFragment(currentTab)
                                }
                            }
                        }

                        override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                            Log.d(TAG, "onFailure: ${t.message}")
                        }

                    })
                dialog.dismiss()

            }

        }

    }

    private fun loadListFragment(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            1 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, KoreanFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            2 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, ChineseFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            3 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, JapaneseFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            4 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, WesternFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            5 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, FlourBasedFoodFragment(binding.fragmentHomeTvDongName.text.toString()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            Log.d(TAG, "onLocationResult()")
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {

        mLastLocation = location
        if (check == false) {
            if (mLastLocation != null) {
                currentLocation()
                check = true
            }
        }

    }

    fun startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates()")

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "startLocationUpdates() 두 위치 권한중 하나라도 없는 경우 ")
            return
        }

        Log.d(TAG, "startLocationUpdates() 위치 권한이 하나라도 존재하는 경우")

        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

}
