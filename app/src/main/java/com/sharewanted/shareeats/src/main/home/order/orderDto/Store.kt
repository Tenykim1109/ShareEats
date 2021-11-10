package com.sharewanted.shareeats.src.main.home.order.orderDto

data class Store (val storeId: String, val name: String, val profile: String, val location: String, val tel: String, val time: String, val info: String, val menuList: MutableList<StoreMenu>, val minPrice: Int)