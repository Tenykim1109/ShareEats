package com.sharewanted.shareeats.src.main.home.order.selectLocation

data class LatLngCoords(val lng: Double, val lat: Double) {

    override fun toString(): String {
        return String.format("%.7f,%.7f", lng, lat)
    }
}
