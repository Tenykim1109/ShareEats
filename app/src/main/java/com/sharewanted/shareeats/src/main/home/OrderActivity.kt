package com.sharewanted.shareeats.src.main.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityOrderBinding


class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // custom Toolbar 적용
        val toolbar = binding.activityOrderToolbar
        setSupportActionBar(toolbar)

        // 툴바 x버튼, 확인 버튼 클릭 테스트
        binding.activityOrderBtnSave.setOnClickListener {
            Toast.makeText(this, "확인", Toast.LENGTH_SHORT).show()
        }

        binding.activityOrderBtnCancel.setOnClickListener {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
        }
    }
}