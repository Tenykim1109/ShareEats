package com.sharewanted.shareeats.config

import java.text.DecimalFormat

class CommonUtils {

    //천단위 콤마
    fun makeComma(num: Int): String {
        var comma = DecimalFormat("#,###")
        return "${comma.format(num)} 원"
    }
}