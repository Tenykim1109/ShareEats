package com.sharewanted.shareeats.src.main.location.model

data class Addresse(
    val addressElements: List<AddressElement>,
    val distance: Double,
    val englishAddress: String,
    val jibunAddress: String,
    val roadAddress: String,
    val x: Double,
    val y: Double
)