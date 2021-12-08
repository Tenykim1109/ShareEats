package com.sharewanted.shareeats.src.main.mypage.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityNoticeContentBinding

class NoticeContentActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoticeContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notice = intent.getSerializableExtra("notice") as Notice

        binding.noticeContentTvTitle.text = notice.title
        binding.noticeContentTvContent.text = notice.content
        binding.noticeContentTvDate.text = notice.Date

        binding.noticeContentIvArrow.setOnClickListener {
            finish()
        }
    }
}