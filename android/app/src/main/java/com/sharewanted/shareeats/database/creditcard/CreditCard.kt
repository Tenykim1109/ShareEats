package com.sharewanted.shareeats.database.creditcard

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "creditcard")
data class CreditCard (
    var userId: String,
    var company: String,
    var number: String,
    var expiry: String,
    var cvc: String,
    var password: String
    ): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(_id: Long, _userId: String, _company: String, _number: String, _expiry: String, _cvc: String, _password: String
        ): this(_userId, _company, _number, _expiry, _cvc, _password) {
        id = _id
    }
}