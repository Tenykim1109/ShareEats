package com.sharewanted.shareeats.src.main.mypage.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityCreatorBinding
import com.sharewanted.shareeats.databinding.ActivityNoticeBinding
import com.sharewanted.shareeats.src.main.mypage.creator.Creator
import com.sharewanted.shareeats.src.main.mypage.creator.CreatorAdapter

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNoticeBinding
    private lateinit var adapter: NoticeAdapter
    private var list = mutableListOf<Notice>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    fun initView() {
        adapter = NoticeAdapter(list)
        binding.fragmentMyPageNoticeRv.adapter = adapter
        binding.fragmentMyPageNoticeRv.layoutManager = LinearLayoutManager(this)
    }

    fun initData() {
        list.add(Notice("서비스 개선 안내문", "2021.10.29", "안녕하십니까아아아"))
        list.add(Notice("서비스 개선 안내문2", "2021.10.29", "안녕하십니까아아아"))
        list.add(Notice("서비스 개선 안내문3", "2021.10.29", "안녕하십니까아아아"))
        list.add(Notice("서비스 개선 안내문4", "2021.10.29", "안녕하십니까아아아"))
    }
}