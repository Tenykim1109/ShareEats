package com.sharewanted.shareeats.src.main.userlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.databaseReference
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.databinding.ActivityLoginBinding
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto
import com.sharewanted.shareeats.src.main.userlogin.find.FindIdActivity
import com.sharewanted.shareeats.src.main.userlogin.find.FindPasswordActivity

private const val TAG = "LoginActivity_싸피"
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAutoLogin()
        initEvent()
    }

    private fun checkAutoLogin() {
        //로그인 된 상태인지 확인
        var user = sharedPreferencesUtil.getUser()

        //로그인 상태 확인. id가 있다면 로그인 된 상태
        if (user.id != ""){
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }
    }

    private fun initEvent() {
        binding.activityLoginBtnLogin.setOnClickListener {
            login(binding.activityLoginEtId.text.toString(), binding.activityLoginEtPassword.text.toString())
        }

        binding.activityLoginTvJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginTvFindId.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginTvFindPassword.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(myId: String, myPassword: String) {
        databaseReference
            .child("User")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == User
                    p0.children.forEach {
                        // it == 유저 별 정보
                        // key(id)와 현재 입력한 id가 일치한다면

                        if(it.key == myId) {
                            var userId = ""
                            var userName = ""
                            var userPassword = ""
                            var userTel = ""
                            var userEmail = ""
                            var userProfile = ""
                            //최근 postId 추가
                            var lastPostId = ""

                            it.children.forEach { user ->
                                when(user.key) {
                                    "id" -> userId = user.value.toString()
                                    "name" -> userName = user.value.toString()
                                    "password" -> userPassword = user.value.toString()
                                    "tel" -> userTel = user.value.toString()
                                    "email" -> userEmail = user.value.toString()
                                    "profile" -> userProfile = user.value.toString()
                                    //최근 postId 추가를 위한 리스트
                                    "postList" -> if (user.child("postList").hasChildren()) {
                                        lastPostId = user.child("postList").children.last().key!!.toInt().toString()
                                    }
                                }
                            }

                            if(myPassword == userPassword) {
                                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                sharedPreferencesUtil.addUser(UserDto(userId, userPassword, userName, userTel, userEmail, userProfile, lastPostId, mutableListOf()))
                                //최근 postId 구독
                                Firebase.messaging.subscribeToTopic(lastPostId)
                                    .addOnCompleteListener { task ->
                                        var msg = getString(R.string.msg_subscribed)
                                        if (!task.isSuccessful) {
                                            msg = getString(R.string.msg_subscribe_failed)
                                        }
                                        Log.d(TAG, "task results : ${task}")
                                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                                    }
                                startActivity(intent)
                                return
                            }
                        }
                    }

                    Toast.makeText(this@LoginActivity, "일치하는 로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }
}