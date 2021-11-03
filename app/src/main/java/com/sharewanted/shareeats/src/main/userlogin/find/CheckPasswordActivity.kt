package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.databinding.ActivityCheckPasswordBinding
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity

private const val TAG = "CheckPasswordActivity_싸피"
class CheckPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityCheckPasswordBtnBack.setOnClickListener { finish() }

        binding.activityCheckPasswordBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}