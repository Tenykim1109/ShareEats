package com.sharewanted.shareeats.src.main.location.model

data class AddressElement(
    val code: String,
    val longName: String,
    val shortName: String,
    val types: List<String>
)