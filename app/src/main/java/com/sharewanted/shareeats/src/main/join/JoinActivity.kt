package com.sharewanted.shareeats.src.main.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.databinding.ActivityJoinBinding

private const val TAG = "JoinActivity_싸피"
class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityJoinBtnBack.setOnClickListener {
            finish()
        }

        binding.activityJoinBtnJoin.setOnClickListener {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show()
        }

        binding.activityJoinBtnCheck.setOnClickListener {
            Toast.makeText(this, "아이디 중복 확인", Toast.LENGTH_SHORT).show()
        }
    }
}