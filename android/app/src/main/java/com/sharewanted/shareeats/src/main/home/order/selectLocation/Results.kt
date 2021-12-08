package com.sharewanted.shareeats.src.main.home.order.selectLocation

import com.google.gson.annotations.SerializedName

data class Results (@SerializedName("region") val region: Region, @SerializedName("land") val land: Land)