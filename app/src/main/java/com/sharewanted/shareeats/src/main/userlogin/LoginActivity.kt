package com.sharewanted.shareeats.src.main.userlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.databinding.ActivityLoginBinding
import com.sharewanted.shareeats.src.main.userlogin.find.FindIdActivity
import com.sharewanted.shareeats.src.main.userlogin.find.FindPasswordActivity

private const val TAG = "LoginActivity_싸피"
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityLoginBtnLogin.setOnClickListener {
            val id = binding.activityLoginEtLogin.text
            val pw = binding.activityLoginEtPassword.text

            //db에서 user 체크

            Toast.makeText(this, "로그인 $id $pw", Toast.LENGTH_SHORT).show()
        }

        binding.activityLoginTvJoin.setOnClickListener {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginTvFindId.setOnClickListener {
            Toast.makeText(this, "아이디 찾기", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginTvFindPassword.setOnClickListener {
            Toast.makeText(this, "비밀번호 찾기", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}