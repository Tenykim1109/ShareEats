package com.sharewanted.shareeats.src.main.userlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sharewanted.shareeats.databinding.ActivityJoinBinding
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

private const val TAG = "JoinActivity_싸피"
class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    var duplicateId: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityJoinBtnBack.setOnClickListener {
            finish()
        }

        binding.activityJoinBtnJoin.setOnClickListener {
            
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show()

            // db에 user insert
            var user = UserDto(
                binding.activityJoinEtId.text.toString(),
                binding.activityJoinEtPassword.text.toString(),
                binding.activityJoinEtName.text.toString(),
                binding.activityJoinEtPhoneNumber.text.toString(),
                binding.activityJoinEtEmail.text.toString(),
                ""
            )

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.activityJoinBtnCheck.setOnClickListener {
            val id = binding.activityJoinEtId.text.toString()

            // db에서 아이디로 검색해서 중복되는 아이디 있는지 확인
            val tempId = "ssafy"

            if (tempId == id) {
                Toast.makeText(this, "사용할 수 없는 아이디입니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}