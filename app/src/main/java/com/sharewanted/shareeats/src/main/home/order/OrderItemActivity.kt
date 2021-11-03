package com.sharewanted.shareeats.src.main.home.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityOrderItemBinding
import com.sharewanted.shareeats.src.main.home.participate.ParticipateActivity

/* 만든이 - 김민정 */
class OrderItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderItemBinding
    private lateinit var adapter: OrderItemAdapter
    private var list = mutableListOf<MenuDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        binding.activityOrderItemBtnCancel.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.activityOrderItemBtnJoin.setOnClickListener {
            val intent = Intent(this, ParticipateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        adapter = OrderItemAdapter(list)
        binding.activityOrderItemRvMenu.adapter = adapter
        binding.activityOrderItemRvMenu.layoutManager = LinearLayoutManager(this)
    }
}