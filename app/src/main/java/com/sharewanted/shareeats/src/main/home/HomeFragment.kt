package com.sharewanted.shareeats.src.main.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.FragmentHomeBinding
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.home.fragment.*
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.postInfo.PostInfoActivity
import com.sharewanted.shareeats.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var postList = mutableListOf<Post>()
    private val mDatabase = Firebase.database.reference
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>((activity as AppCompatActivity).applicationContext, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.location_array))
        binding.homeSpinner.adapter = adapter

        initView()
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

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        binding.fragmentHomeTlMenuType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadListFragment(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                loadListFragment(tab)
            }

        })

    }

    private fun loadListFragment(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, TotalFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            1 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, KoreanFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            2 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, ChineseFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            3 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, JapaneseFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            4 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, WesternFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            5 -> childFragmentManager.beginTransaction()
                .replace(R.id.fragment_home_fcv_tab_host, FlourBasedFoodFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_spinner_menu, menu)
    }
}
