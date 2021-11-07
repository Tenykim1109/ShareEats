package com.sharewanted.shareeats.util

import android.content.Context
import android.content.SharedPreferences
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

class SharedPreferencesUtil(context: Context) {
    val SHARED_PREFERENCES_NAME = "shareEats_preference"

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //사용자 정보 저장
    fun addUser(user: UserDto) {
        val editor = preferences.edit()
        editor.putString("id", user.id)
        editor.putString("name", user.name)
        editor.putString("password", user.password)
        editor.putString("tel", user.tel)
        editor.putString("email", user.email)
        editor.apply()
    }

    fun getUser(): UserDto {
        val id = preferences.getString("id", "")
        if (id != "") {
            val name = preferences.getString("name", "")
            val password = preferences.getString("password", "")
            val tel = preferences.getString("tel", "")
            val email = preferences.getString("email", "")
            return UserDto(id!!, password!!, name!!, tel!!, email!!, "")
        } else {
            return UserDto()
        }
    }

    fun updateUser(user: UserDto) {
        val editor = preferences.edit()
        editor.putString("password", user.password)
        editor.putString("email", user.email)
        editor.apply()
    }

    fun deleteUser() {
        //preference 지우기
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}