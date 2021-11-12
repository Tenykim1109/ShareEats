package com.sharewanted.shareeats.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.sharewanted.shareeats.databinding.FragmentHomeBinding
import com.sharewanted.shareeats.src.main.home.fragment.*
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.postInfo.PostInfoActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var param1: String? = null
    private var param2: String? = null

    private var postList = mutableListOf<Post>()
    private val mDatabase = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
                    val post = dataSnapshot.getValue(Post::class.java)

                    postList.add(post!!)
                }

                val searchTitleAdapter = SearchTitleAdapter(requireContext(), R.layout.fragment_home_list_item_search_title, postList)
                binding.fragmentHomeActvSearchTitle.setAdapter(searchTitleAdapter)
                binding.fragmentHomeActvSearchTitle.setOnItemClickListener { adapterView, view, i, l ->
                    val selectedPost = postList[i]
                    val intent = Intent(requireContext(), PostInfoActivity::class.java).apply {
                        putExtra("postId", selectedPost.postId)
                    }
                    startActivity(intent)
                    binding.fragmentHomeActvSearchTitle.setText("")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Post error", error.toString())
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }

        })



        binding.fbWritePost.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java)
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
