package com.sharewanted.shareeats.src.main.userlogin.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.databinding.ActivityFindPasswordBinding
import com.sharewanted.shareeats.src.main.userlogin.JoinActivity

private const val TAG = "FindPasswordActivity_싸피"
class FindPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
    }

    private fun initEvent() {
        // 뒤로가기 버튼
        binding.activityFindPasswordBtnBack.setOnClickListener { finish() }

        // 아이디 찾기 버튼
        binding.activityFindPasswordBtnFindPassword.setOnClickListener {
            findPasswordOnFirebase()
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

    private fun findPasswordOnFirebase() {
        var myName = binding.activityFindPasswordEtName.text.toString()
        var myId = binding.activityFindPasswordEtId.text.toString()

        ApplicationClass.databaseReference
            .child("User")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == User
                    p0.children.forEach {
                        var userName = ""
                        var userId = ""
                        var userPassword = ""

                        if(it.key == myId) {
                            it.children.forEach { user ->
                                when(user.key) {
                                    "name" -> userName = user.value.toString()
                                    "id" -> userId = user.value.toString()
                                    "password" -> userPassword = user.value.toString()
                                }
                            }

                            if(myName == userName) {
                                var intent = Intent(this@FindPasswordActivity, CheckPasswordActivity::class.java)
                                intent.putExtra("id", userId)
                                intent.putExtra("password", userPassword)
                                startActivity(intent)
                                return
                            }
                        }
                    }

                    Toast.makeText(this@FindPasswordActivity, "일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                }
            })
    }
}