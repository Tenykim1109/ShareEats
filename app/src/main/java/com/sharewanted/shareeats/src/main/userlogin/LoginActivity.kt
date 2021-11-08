package com.sharewanted.shareeats.src.main.userlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.databinding.ActivityLoginBinding
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.mypage.edituser.EditUserActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto
import com.sharewanted.shareeats.src.main.userlogin.find.CheckPasswordActivity
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

    private fun login(myId: String, myPassword: String) {
        ApplicationClass.databaseReference
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

                            it.children.forEach { user ->
                                when(user.key) {
                                    "id" -> userId = user.value.toString()
                                    "name" -> userName = user.value.toString()
                                    "password" -> userPassword = user.value.toString()
                                    "tel" -> userTel = user.value.toString()
                                    "email" -> userEmail = user.value.toString()
                                    "profile" -> userProfile = user.value.toString()
                                }
                            }

                            if(myPassword == userPassword) {
                                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sharedPreferencesUtil.addUser(UserDto(userId, userPassword, userName, userTel, userEmail, userProfile))
                                startActivity(intent)
                            }
                        }
                    }
                }
            })
    }
}