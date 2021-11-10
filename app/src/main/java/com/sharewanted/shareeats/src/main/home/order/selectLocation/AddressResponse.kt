package com.sharewanted.shareeats.src.main.home.order.selectLocation

import com.google.gson.annotations.SerializedName

data class AddressResponse(@SerializedName("results") val results: List<Results>)
