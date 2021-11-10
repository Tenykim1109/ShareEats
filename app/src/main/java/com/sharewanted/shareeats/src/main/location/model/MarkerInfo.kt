package com.sharewanted.shareeats.src.main.location.model

import com.naver.maps.map.overlay.Marker

data class MarkerInfo(val marker: Marker, val storeName: String, val title: String) {
    constructor(): this(Marker(), "", "")
}