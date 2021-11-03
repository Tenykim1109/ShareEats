package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.databinding.ActivityFindIdBinding
import com.sharewanted.shareeats.src.main.userlogin.JoinActivity

private const val TAG = "FindIdActivity_싸피"
class FindIdActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityFindIdBtnBack.setOnClickListener { finish() }

        binding.activityFindIdBtnFindId.setOnClickListener {
            val intent = Intent(this, CheckIdActivity::class.java).apply {
                putExtra("name", binding.activityFindIdEtName.text.toString())
                putExtra("email", binding.activityFindIdEtEmail.text.toString())
            }
            startActivity(intent)
        }

        binding.activityFindIdTvJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.activityFindIdTvFindPassword.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}