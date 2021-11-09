package com.sharewanted.shareeats.src.main.home.order.orderDto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreMenu (val name: String, val price: Int, val photo: String, val desc: String) : Parcelable {
    var quantity: Int = 0
    constructor(name: String, price: Int, photo: String, desc: String, quantity: Int) : this(name, price, photo, desc) {
        this.quantity = quantity
    }
}