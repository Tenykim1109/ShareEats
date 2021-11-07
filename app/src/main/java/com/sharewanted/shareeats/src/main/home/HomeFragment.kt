package com.sharewanted.shareeats.src.main.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.FragmentHomeBinding
import com.sharewanted.shareeats.src.main.home.order.OrderActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private var param1: String? = null
    private var param2: String? = null
    private var list = mutableListOf<HomeMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        adapter = HomeAdapter(list)
        binding.fragmentHomeRvMain.adapter = adapter
        binding.fragmentHomeRvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.fbWritePost.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java)
            startActivity(intent)
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
