package com.sharewanted.shareeats.src.main.home.order.orderDto

data class Post (val postId: Int = 0,
                 val title: String = "",
                 val date: Long = 0,
                 val userId: String = "",
                 val storeId: String = "",
                 val place: String = "",
                 val closedTime: Long = 0,
                 val content: String = "",
                 val fund: Int = 0,
                 val minPrice: Int = 0,
                 val completed: String = "",
                 val type: String = "")