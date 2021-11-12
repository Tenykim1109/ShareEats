package com.sharewanted.shareeats.src.main.mypage.creator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityCreatorBinding

// 만든이 페이지
class CreatorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreatorBinding
    private lateinit var adapter: CreatorAdapter
    private var list = mutableListOf<Creator>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    fun initView() {
        adapter = CreatorAdapter(list)
        binding.fragmentMyPageRvMenu.adapter = adapter
        binding.fragmentMyPageRvMenu.layoutManager = LinearLayoutManager(this)

        binding.fragmentMyPageNoticeIvArrow.setOnClickListener {
            finish()
        }
    }

    fun initData() {
        list.add(Creator("김민정", "lona573@gmail.com"))
        list.add(Creator("김주환", "juhwan.dev@gmail.com"))
        list.add(Creator("나요셉", "js.pekah@gmail.com"))
        list.add(Creator("백동열", "piece1707@gmail.com"))
    }
}