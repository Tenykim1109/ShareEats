package com.sharewanted.shareeats.src.main.userlogin.dto

data class UserDto(val id: String, var password: String, val name: String, val tel: String, var email: String, val profile: String) {
    constructor(): this("", "", "", "", "", "")
}