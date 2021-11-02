package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.databinding.ActivityFindPasswordBinding
import com.sharewanted.shareeats.src.main.userlogin.JoinActivity

private const val TAG = "FindPasswordActivity_싸피"
class FindPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityFindPasswordBtnBack.setOnClickListener { finish() }

        binding.activityFindPasswordBtnFindPassword.setOnClickListener {
            val intent = Intent(this, CheckPasswordActivity::class.java).apply {
                putExtra("name", binding.activityFindPasswordEtName.text.toString())
                putExtra("id", binding.activityFindPasswordEtId.text.toString())
            }
            startActivity(intent)
        }

        binding.activityFindPasswordTvJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.activityFindPasswordTvFindId.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }
    }
}