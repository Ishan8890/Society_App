package com.gayatrikunj.societyapp

import com.gayatrikunj.societyapp.Model.MemberData

class InsertMember {

    companion object member{
        fun getInsertingData(date:String,expense:String,perHead:Float,type:String,month:Int,reason:String):ArrayList<MemberData>{
            var list = ArrayList<MemberData>()
            var data = MemberData()
            data.name = "Ashok Shah"
            data.address = "First floor (Front side)"
            data.expenseAmount = expense.toFloat()
            data.date = date
            data.individualAmount = perHead
            data.month = month
            data.transactionType = type
            data.reason = reason
            list.add(data)
            var data1 = MemberData()
            data1.name = "Ajit Bhatnagar"
            data1.address = "Second floor (Front side)"
            data1.expenseAmount = expense.toFloat()
            data1.date = date
            data1.individualAmount = perHead
            data1.month = month
            data1.transactionType = type
            data1.reason = reason
            list.add(data1)
            var data2 = MemberData()
            data2.name = "Dinesh Rustogi"
            data2.address = "Third floor (Front side)"
            data2.expenseAmount = expense.toFloat()
            data2.date = date
            data2.individualAmount = perHead
            data2.month = month
            data2.transactionType = type
            data2.reason = reason
            list.add(data2)
            var data3 = MemberData()
            data3.name = "Aditya Tyagi"
            data3.address = "Fourth floor (Front side)"
            data3.expenseAmount = expense.toFloat()
            data3.date = date
            data3.month = month
            data3.individualAmount = perHead
            data3.transactionType = type
            data3.reason = reason
            list.add(data3)
            var data4 = MemberData()
            data4.name = "Pankaj Belwal (Kamala)"
            data4.address = "First floor (Back side)"
            data4.expenseAmount = expense.toFloat()
            data4.date = date
            data4.month = month
            data4.individualAmount = perHead
            data4.transactionType = type
            data4.reason = reason
            list.add(data4)
            var data5 = MemberData()
            data5.name = "Rajiv Tyagi"
            data5.address = "Second floor (Back side)"
            data5.expenseAmount = expense.toFloat()
            data5.date = date
            data5.month = month
            data5.individualAmount = perHead
            data5.transactionType = type
            data5.reason = reason
            list.add(data5)
            var data6 = MemberData()
            data6.name = "Sukhbir Singh"
            data6.address = "Third floor (Back side)"
            data6.expenseAmount = expense.toFloat()
            data6.date = date
            data6.month = month
            data6.individualAmount = perHead
            data6.transactionType = type
            data6.reason = reason
            list.add(data6)
            var data7 = MemberData()
            data7.name = "Hira"
            data7.address = "Fourth floor (Back side)"
            data7.expenseAmount = expense.toFloat()
            data7.date = date
            data7.month = month
            data7.individualAmount = perHead
            data7.transactionType = type
            data7.reason = reason
            list.add(data7)
            return list
        }

    }
}