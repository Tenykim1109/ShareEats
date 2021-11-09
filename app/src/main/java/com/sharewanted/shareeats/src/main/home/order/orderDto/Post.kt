package com.sharewanted.shareeats.src.main.home.order.orderDto

data class Post (val postId: Int,
                 val title: String,
                 val date: Long,
                 val userId: String,
                 val storeId: String,
                 val place: String,
                 val closedTime: Long,
                 val content: String,
                 val fund: Int,
                 val minPrice: Int,
                 val completed: String,
                 val type: String)