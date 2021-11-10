package com.sharewanted.shareeats.database.creditcard

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CreditCard::class], version = 1)
abstract class CreditCardDatabase: RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao
}