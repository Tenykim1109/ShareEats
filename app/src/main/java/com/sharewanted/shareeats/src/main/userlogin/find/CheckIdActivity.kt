package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.databinding.ActivityCheckIdBinding
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity

private const val TAG = "CheckIdActivity_싸피"
class CheckIdActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
    }

    fun initView() {
        var id = intent.getStringExtra("id")!!
        id = id.replaceRange(3, id.length, "*")
        var name = intent.getStringExtra("name")

        var result = "[${name}] 님의 아이디는\n${id}입니다."
        binding.activityCheckIdTvResult.text = result
    }

    fun initEvent() {
        binding.activityCheckIdBtnBack.setOnClickListener {
            finish()
        }

        binding.activityCheckIdBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.activityCheckIdBtnCheckPassword.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}