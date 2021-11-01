package com.sharewanted.shareeats.src.main.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityLoginBinding
import com.sharewanted.shareeats.src.main.join.JoinActivity

private const val TAG = "LoginActivity_싸피"
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityLoginBtnLogin.setOnClickListener {
            Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show()
        }

        binding.activityLoginTvJoin.setOnClickListener {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginTvFindId.setOnClickListener {
            Toast.makeText(this, "아이디 찾기", Toast.LENGTH_SHORT).show()
        }

        binding.activityLoginTvFindPassword.setOnClickListener {
            Toast.makeText(this, "비밀번호 찾기", Toast.LENGTH_SHORT).show()
        }
    }
}