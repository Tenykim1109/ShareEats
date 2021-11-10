package com.sharewanted.shareeats.src.main.userlogin.dto

import com.sharewanted.shareeats.src.main.home.order.orderDto.Post

data class UserDto(val id: String, var password: String, val name: String, val tel: String, var email: String, var profile: String, var orderDetailList: MutableList<Int>) {

    constructor(): this("", "", "", "", "", "", mutableListOf<Int>())
}