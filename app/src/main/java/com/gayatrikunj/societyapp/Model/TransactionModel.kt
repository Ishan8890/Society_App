package com.gayatrikunj.societyapp.Model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "transactionData")
class TransactionModel @Inject constructor() {

    @PrimaryKey(autoGenerate = true)
    var memberId: Int = 0

    @ColumnInfo(name = "item")
    var item: String = ""

    @ColumnInfo(name = "credit_amount")
    var credit_amount: Float = 0.0f

    @ColumnInfo(name = "wallet_amount")
    var wallet_amount: Float = 0.0f

    var debit_amount: Float = 0.0f

    var date: String = ""

    var type:String=""

    fun getModel(){
     Log.d("Check:::","Transaction Model class")
    }

}