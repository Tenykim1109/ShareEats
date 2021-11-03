package com.sharewanted.shareeats.src.main.home.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityOrderInfoBinding

class OrderInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderInfoBinding
    private lateinit var adapter: OrderInfoAdapter
    private var list = mutableListOf<MenuDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        adapter = OrderInfoAdapter(list)
        binding.fragmentMyPageOrderInfoRv.adapter = adapter
        binding.fragmentMyPageOrderInfoRv.layoutManager = LinearLayoutManager(this)
    }
}