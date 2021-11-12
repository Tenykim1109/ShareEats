package com.sharewanted.shareeats.src.main.home.order.orderDto

import java.io.Serializable

data class Post (
    var postId: Int = 0,
    val title: String = "",
    val date: Long = 0,
    val userId: String = "",
    val storeId: String = "",
    val place: String = "",
    val closedTime: Long = 0,
    val content: String = "",
    var fund: Int = 0,
    val minPrice: Int = 0,
    val completed: String = "",
    val type: String = ""): Serializable