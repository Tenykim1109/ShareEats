package com.sharewanted.shareeats.src.main.location.model

import com.naver.maps.map.overlay.Marker

data class MarkerInfo(val marker: Marker, val title: String, val storeName: String) {
    constructor(): this(Marker(), "", "")
}