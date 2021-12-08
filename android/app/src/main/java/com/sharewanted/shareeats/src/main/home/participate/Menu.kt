package com.sharewanted.shareeats.src.main.home.participate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
    var name: String,
    var price: Int,
    var quantity: Int,
    var photo: String,
    var desc: String
): Parcelable