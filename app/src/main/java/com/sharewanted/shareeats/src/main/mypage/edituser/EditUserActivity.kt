package com.sharewanted.shareeats.src.main.mypage.edituser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharewanted.shareeats.databinding.ActivityEditUserBinding

private const val TAG = "EditUserActivity_싸피"
class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}