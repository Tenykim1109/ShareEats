package com.sharewanted.shareeats.src.main.location.model

data class Geocode(
    val addresses: List<Addresse>,
    val errorMessage: String,
    val meta: Meta,
    val status: String
)