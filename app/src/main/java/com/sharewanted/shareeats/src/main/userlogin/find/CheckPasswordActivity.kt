package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.ActivityCheckPasswordBinding
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity

private const val TAG = "CheckPasswordActivity_싸피"
class CheckPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
    }

    fun initView() {
        var id = intent.getStringExtra("id")!!

        var password = intent.getStringExtra("password")!!
        password = password.substring(0, 4) + "********"

        var result = "[${id}] 님의 비밀번호는\n${password}입니다."
        binding.activityCheckPasswordTvResult.text = result
    }

    private fun initEvent() {
        binding.activityCheckPasswordBtnBack.setOnClickListener {
            finish()
        }

        binding.activityCheckPasswordBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}