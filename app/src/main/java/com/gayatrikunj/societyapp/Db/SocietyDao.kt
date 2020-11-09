package com.gayatrikunj.societyapp.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.TransactionModel
import java.util.*


@Dao
interface SocietyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: MemberData):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalletData(data: TransactionModel):Long

//    @Query("Select wallet_amount from transactionData order by memberId desc limit 1 ")
//    fun getWalletBalance():LiveData<Float>

    @Query("Select * from transactionData")
    fun getTransactionRecord():LiveData<List<TransactionModel>>

    @Query("select * from memberData")
    fun getMemberData(): LiveData<List<MemberData>>

    @Query("select * from memberData WHERE month =:date AND transactionType=:reason")
    fun getSelectedDate(date: Int,reason:String): LiveData<List<MemberData>>

    @Update
    suspend fun updateMember(member: MemberData)


}