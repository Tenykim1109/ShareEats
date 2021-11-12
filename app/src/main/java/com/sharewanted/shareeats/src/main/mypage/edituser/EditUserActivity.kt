package com.sharewanted.shareeats.src.main.mypage.edituser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.databaseReference
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.databinding.ActivityEditUserBinding
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "EditUserActivity_싸피"
class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding
    var user = sharedPreferencesUtil.getUser()
    var profileImage: Uri? = null

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

        storageRef.child("profile").child(user.profile).downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).circleCrop().override(200, 200).into(binding.activityEditUserIvUser)
        }
    }

    fun initEvent() {
        binding.activityEditUserBtnBack.setOnClickListener { finish() }

        binding.activityEditUserIvUser.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
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
            databaseReference.child("User").child(user.id).removeValue()
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

        // 프로필도 변경했다면
        if(profileImage != null) {
            // storage에서 해당 사진을 삭제하고
            storageRef.child("profile")?.child(user.profile).delete()

            // 새로운 사진을 넣은다음
            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var imgFileName = "IMAGE_" + timeStamp
            storageRef.child("profile")?.child(imgFileName).putFile(profileImage!!)

            // 새로운 사진의 이름을 user에 저장
            databaseReference.child("User").child(user.id).child("profile").setValue(imgFileName)
            user.profile = imgFileName
        }

        databaseReference.child("User").child(user.id).child("email").setValue(email)
        databaseReference.child("User").child(user.id).child("password").setValue(password)

        user.email = email
        user.password = password
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

    // 갤러리에서 사진 선택
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null){
            Glide.with(this).load(data.data).circleCrop().override(200, 200).into(binding.activityEditUserIvUser)
            profileImage = data.data!!
        }
    }
}