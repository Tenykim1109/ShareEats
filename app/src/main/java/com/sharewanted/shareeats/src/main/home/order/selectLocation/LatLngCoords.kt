package com.sharewanted.shareeats.src.main.home.order.selectLocation

data class LatLngCoords(val lat: Double, val lng: Double) {

    override fun toString(): String {
        return String.format("%.7f,%.7f", lat, lng)
    }
}
