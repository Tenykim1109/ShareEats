package com.sharewanted.shareeats.src.main.mypage.edituser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.databinding.ActivityEditUserBinding
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity

private const val TAG = "EditUserActivity_싸피"
class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityEditUserBtnBack.setOnClickListener { finish() }

        binding.activityEditUserIvUser.setOnClickListener {
            // 프사 변경
        }

        binding.activityEditUserTvLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.activityEditUserTvWithdrawal.setOnClickListener {
            // delete user

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.activityEditUserBtnEdit.setOnClickListener {
            // update user

            finish()
        }
    }
}