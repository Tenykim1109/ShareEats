package com.sharewanted.shareeats.database.creditcard

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CreditCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: CreditCard)

    @Query("select * from creditcard")
    fun selectAll(): LiveData<MutableList<CreditCard>>

    @Query("select * from creditcard where userId = (:userId) and company = (:company)")
    fun selectOne(userId: String, company: String): CreditCard

    @Update
    fun update(dto: CreditCard)

}