package com.sharewanted.shareeats.src.main.home.order.orderDto

data class Post (val title: String, val storeId: String, val minPrice: Int, val place: String, val date: Long, val closedTime: Long, val content: String, val participant: List<PersonMenu>)