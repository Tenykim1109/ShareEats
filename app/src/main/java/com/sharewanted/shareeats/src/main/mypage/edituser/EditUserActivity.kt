package com.sharewanted.shareeats.src.main.mypage.edituser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.databinding.ActivityEditUserBinding
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

private const val TAG = "EditUserActivity_싸피"
class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding
    var user = sharedPreferencesUtil.getUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
    }

    fun initView() {
        binding.activityEditUserTvName.setText(user.name)
        binding.activityEditUserEtEmail.setText(user.email)
    }

    fun initEvent() {
        binding.activityEditUserBtnBack.setOnClickListener { finish() }

        binding.activityEditUserIvUser.setOnClickListener {
            // 프사 변경
        }

        binding.activityEditUserTvLogout.setOnClickListener {
            sharedPreferencesUtil.deleteUser()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

        binding.activityEditUserTvWithdrawal.setOnClickListener {
            sharedPreferencesUtil.deleteUser()
            ApplicationClass.databaseReference.child("User").child(user.id).removeValue()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

        binding.activityEditUserBtnEdit.setOnClickListener {
            if(updateUserInfo()) {
                finish()
            }
        }
    }

    private fun updateUserInfo(): Boolean {
        var email = binding.activityEditUserEtEmail.text.toString()
        var password = binding.activityEditUserEtPassword.text.toString()
        var password2 = binding.activityEditUserEtComfirmPassword.text.toString()

        if(hasEmptyInput(email, password, password2) || isNotEqualPassword(password, password2)) {
            return false
        }

        user.email = email
        user.password = password
        ApplicationClass.databaseReference.child("User").child(user.id).setValue(user)
        sharedPreferencesUtil.updateUser(user)

        return true
    }

    private fun hasEmptyInput(email: String, password: String, password2: String): Boolean {
        if(email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    private fun isNotEqualPassword(password: String, password2: String): Boolean {
        if(password != password2) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }
}