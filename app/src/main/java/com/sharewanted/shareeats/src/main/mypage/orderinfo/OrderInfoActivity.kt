package com.sharewanted.shareeats.src.main.mypage.orderinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityOrderInfoBinding
import com.sharewanted.shareeats.src.main.mypage.orderdetail.OrderDetail
import com.sharewanted.shareeats.src.main.mypage.orderdetail.OrderDetailAdapter

class OrderInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderInfoBinding
    private lateinit var adapter: OrderInfoAdapter
    private var list = mutableListOf<OrderInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    fun initView() {
        adapter = OrderInfoAdapter(list)
        binding.fragmentMyPageOrderInfoRv.adapter = adapter
        binding.fragmentMyPageOrderInfoRv.layoutManager = LinearLayoutManager(this)
    }

    fun initData() {
        list.add(OrderInfo(0))
        list.add(OrderInfo(1))
        list.add(OrderInfo(2))
        list.add(OrderInfo(3))
        list.add(OrderInfo(4))
    }
}