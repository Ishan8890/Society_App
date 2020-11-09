package com.gayatrikunj.societyapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "memberData")
class MemberData {

    @PrimaryKey(autoGenerate = true)
    var memberId: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "address")
    var address: String = ""

    @ColumnInfo(name = "total_expense_amount")
    var expenseAmount: Float = 0.0f

    var individualAmount: Float = 0.0f

    var isChecked: Boolean = true

    var reason:String=""

    var date: String = ""

    var month:Int=0

    var transactionType:String=""
}

//@Entity(tableName = "TB_ENTITY")
//class MyEntity {
//    @ColumnInfo(name = "createDate")
//    @TypeConverters(LocalDateConverter::class)
//    var createDate: Date? = null
//}