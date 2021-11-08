package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.ActivityFindIdBinding
import com.sharewanted.shareeats.src.main.userlogin.JoinActivity

private const val TAG = "FindIdActivity_싸피"
class FindIdActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
    }

    private fun initEvent() {
        // 뒤로가기 버튼
        binding.activityFindIdBtnBack.setOnClickListener { finish() }

        // 아이디 찾기 버튼
        binding.activityFindIdBtnFindId.setOnClickListener {
            findIdOnFirebase()
            //Toast.makeText(this, "일치하는 아이디가 없습니다", Toast.LENGTH_SHORT).show()
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

    private fun findIdOnFirebase() {
        var myName = binding.activityFindIdEtName.text.toString()
        var myEmail = binding.activityFindIdEtEmail.text.toString()
        var userName = ""
        var userEmail = ""
        var userId = ""

        ApplicationClass.databaseReference
            .child("User")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == User
                    p0.children.forEach {
                        // it == 유저 별 해시코드
                        it.children.forEach { user ->
                            when(user.key) {
                                "name" -> userName = user.value.toString()
                                "email" -> userEmail = user.value.toString()
                                "id" -> userId = user.value.toString()
                            }
                        }

                        if(myName == userName && myEmail == userEmail) {
                            var intent = Intent(this@FindIdActivity, CheckIdActivity::class.java)
                            intent.putExtra("id", userId)
                            intent.putExtra("name", userName)
                            startActivity(intent)
                        }
                    }
                }
            })
    }
}