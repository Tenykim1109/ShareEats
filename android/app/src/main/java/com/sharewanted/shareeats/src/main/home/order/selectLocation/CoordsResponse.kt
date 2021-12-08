package com.sharewanted.shareeats.src.main.home.order.selectLocation

import com.google.gson.annotations.SerializedName

data class CoordsResponse(@SerializedName("addresses") val coords: List<Coords>)
