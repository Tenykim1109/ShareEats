package com.sharewanted.shareeats.src.main.home.participate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityParticipateBinding

class ParticipateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParticipateBinding
    private lateinit var adapter: ParticipateAdapter
    private var list = mutableListOf<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.activityParticipateToolbar
        setSupportActionBar(toolbar)

        initView()
    }

    // RecyclerView 초기화
    private fun initView() {
        adapter = ParticipateAdapter(list)
        binding.activityParticipateRvMenu.adapter = adapter
        binding.activityParticipateRvMenu.layoutManager = LinearLayoutManager(this)
    }
}