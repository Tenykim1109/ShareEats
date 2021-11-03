package com.sharewanted.shareeats.src.main.mypage.orderdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityCreatorBinding
import com.sharewanted.shareeats.databinding.ActivityOrderDetailBinding
import com.sharewanted.shareeats.src.main.mypage.creator.Creator
import com.sharewanted.shareeats.src.main.mypage.creator.CreatorAdapter

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter
    private var list = mutableListOf<OrderDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    fun initView() {
        adapter = OrderDetailAdapter(list)
        binding.fragmentMyPageOrderDetailRv.adapter = adapter
        binding.fragmentMyPageOrderDetailRv.layoutManager = LinearLayoutManager(this)
    }
}