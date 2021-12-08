package com.sharewanted.shareeats.database.creditcard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.withTransaction
import java.lang.IllegalStateException

private const val DATABASE_NAME = "creditcard-database.db"
private const val TAG = "CreditCardRepository_싸피"
class CreditCardRepository private constructor(context: Context) {

    private val database: CreditCardDatabase = Room.databaseBuilder(
        context.applicationContext,
        CreditCardDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val creditCardDao = database.creditCardDao()

    fun getList(): LiveData<MutableList<CreditCard>> = creditCardDao.selectAll()

    fun getCreditCard(userId: String, company: String): CreditCard = creditCardDao.selectOne(userId, company)

    suspend fun insert(dto: CreditCard) = database.withTransaction {
        creditCardDao.insert(dto)
    }

    suspend fun update(dto: CreditCard) = database.withTransaction {
        creditCardDao.update(dto)
    }

    companion object {
        private var INSTANCE: CreditCardRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CreditCardRepository(context)
            }
        }

        fun get(): CreditCardRepository {
            return INSTANCE ?:
            throw IllegalStateException("CreditCardRepository must be initialized")
        }
    }
}