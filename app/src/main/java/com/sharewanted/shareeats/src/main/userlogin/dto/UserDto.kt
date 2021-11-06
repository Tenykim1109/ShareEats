package com.sharewanted.shareeats.src.main.userlogin.dto

data class UserDto(val id: String, val password: String, val name: String, val tel: String, val email: String, val profile: String) {
    constructor(): this("", "", "", "", "", "")
}