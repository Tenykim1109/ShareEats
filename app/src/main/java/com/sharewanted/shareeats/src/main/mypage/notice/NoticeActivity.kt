package com.sharewanted.shareeats.src.main.mypage.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        adapter.setItemClickListener(object: NoticeAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@NoticeActivity, NoticeContentActivity::class.java).apply {
                    putExtra("notice", list[position])
                }

                startActivity(intent)
            }
        })
    }

    fun initView() {
        adapter = NoticeAdapter(list)
        binding.fragmentMyPageNoticeRv.adapter = adapter
        binding.fragmentMyPageNoticeRv.layoutManager = LinearLayoutManager(this)
    }

    fun initData() {
        list.add(Notice("서비스 개선 안내문", "2021.10.29", "안녕하십니까아아아\n1\n2\n2\n3\n4\n5\n6\n7\n8\n9\n안녕"))
        list.add(Notice("서비스 개선 안내문2", "2021.10.29", "안녕하십니까아아아"))
        list.add(Notice("서비스 개선 안내문3", "2021.10.29", "안녕하십니까아아아"))
        list.add(Notice("서비스 개선 안내문4", "2021.10.29", "안녕하십니까아아아"))
    }
}