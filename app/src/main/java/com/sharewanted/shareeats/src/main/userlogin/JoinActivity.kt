package com.sharewanted.shareeats.src.main.userlogin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass.Companion.databaseReference
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.databinding.ActivityJoinBinding
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "JoinActivity_싸피"
class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    var users = mutableListOf<String>()
    var duplicateId = false
    lateinit var profileImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
        initFirebase()
    }

    private fun initEvent() {
        // 중복 확인 버튼
        binding.activityJoinBtnCheck.setOnClickListener {
            duplicateCheck()
        }

        // 회원가입 버튼
        binding.activityJoinBtnJoin.setOnClickListener {
            if(register()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.activityJoinIvProfile.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
        }
    }

    private fun initFirebase() {
        databaseReference
            .child("User")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == User
                    p0.children.forEach {
                        // it == 유저 별 해시코드
                        it.children.forEach { user ->
                            users.add(user.value.toString())
                        }
                    }
                }
            })
    }

    // 갤러리에서 사진 선택
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null){
            Glide.with(this).load(data.data).circleCrop().override(100, 100).into(binding.activityJoinIvProfile)
            profileImage = data.data!!
        }
    }

    private fun duplicateCheck() : Boolean {
        var id = binding.activityJoinEtId.text.toString()
        if(id.isEmpty()) {
            Toast.makeText(this, "ID를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if(users.isEmpty()) {
            Toast.makeText(this, "서버와 통신에 문제가 있습니다", Toast.LENGTH_SHORT).show()
        } else if(users.contains(id)) {
            Toast.makeText(this, "이미 가입된 아이디입니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show()
            duplicateId = true
            return true
        }

        return false
    }

    private fun register(): Boolean{
        var id = binding.activityJoinEtId.text.toString()
        var password = binding.activityJoinEtPassword.text.toString()
        var password2 = binding.activityJoinEtConfirmPassword.text.toString()
        var name = binding.activityJoinEtName.text.toString()
        var tel = binding.activityJoinEtTel.text.toString()
        var email = binding.activityJoinEtEmail.text.toString()
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var profile = "IMAGE_" + timeStamp
        var user = UserDto(id, password, name, tel, email, profile, mutableListOf())

        if(hasEmptyInput(user, password2) || isNotEqualPassword(password, password2)) {
            return false
        }

        if(profileImage == null) {
            Toast.makeText(this, "프로필을 설정해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        if(duplicateId) {
            lifecycleScope.launch(Dispatchers.IO){
                // 회원 테이블에 추가
                databaseReference.child("User").child(user.id).setValue(user)

                // Firebase Storage에 프로필 사진 추가
                storageRef.child("profile")?.child(profile).putFile(profileImage)
            }
            Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(this, "id 중복 확인이 필요합니다", Toast.LENGTH_SHORT).show()
        }

        return false
    }

    private fun hasEmptyInput(user: UserDto, password2: String): Boolean {
        if(user.id.isEmpty() || user.password.isEmpty() || password2.isEmpty() ||
                user.name.isEmpty() || user.tel.isEmpty() || user.email.isEmpty()) {
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