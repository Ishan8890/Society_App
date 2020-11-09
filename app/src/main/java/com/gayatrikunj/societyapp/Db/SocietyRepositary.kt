package com.gayatrikunj.societyapp.Db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.TransactionModel

class SocietyRepositary(var dao: SocietyDao) {

    val liveDataMerger = MediatorLiveData<Float>()
    val allMembers: LiveData<List<MemberData>> = dao.getMemberData()

    suspend fun insert(memberList: List<MemberData>): Long {
        var record: Long = 0
        for (i in memberList) {
            var data = MemberData()
            data.name = i.name
            data.address = i.address
            data.date = i.date
            data.month = i.month
            data.expenseAmount = i.expenseAmount
            data.individualAmount = i.individualAmount
            data.isChecked = i.isChecked
            data.reason = i.reason
            data.transactionType = i.transactionType
            record = dao.insertUsers(data)
        }
        return record
    }

    suspend fun insertWallet(data:TransactionModel):Long{
        var record:Long =0
       record = dao.insertWalletData(data)
        return record
    }
    fun getUserData(date: Int,reason:String): LiveData<List<MemberData>> {
        return dao.getSelectedDate(date,reason)
    }

//    fun getWalletAmount():LiveData<Float>{
//        return dao.getWalletBalance()
//    }

    fun getTransactionRecord():LiveData<List<TransactionModel>>{
        return  dao.getTransactionRecord()
    }

//    fun getValue():LiveData<Float>{
//        val liveDataMerger = MediatorLiveData<Float>()
//        liveDataMerger.addSource(dao.getWalletBalance(), object : androidx.lifecycle.Observer<Float> {
//            override
//            fun onChanged(s: Float) {
//                liveDataMerger.value = s
//                liveDataMerger.removeSource(dao.getWalletBalance())
//            }
//        })
//        return liveDataMerger
//    }

    fun  LiveData<Float>.getDistinct(): LiveData<Float> {
        val distinctLiveData = MediatorLiveData<Float>()
        distinctLiveData.addSource(this, object : Observer<Float> {
            private var initialized = false
            private var lastObj: Float? = null
            override fun onChanged(obj: Float?) {
                if (!initialized) {
                    initialized = true
                    lastObj = obj
                    distinctLiveData.postValue(lastObj)
                } else if ((obj == null && lastObj != null)
                    || obj != lastObj) {
                    lastObj = obj
                    distinctLiveData.postValue(lastObj)
                }
            }
        })
        return distinctLiveData
    }


    suspend fun updateMember(memberData: MemberData) {
        dao.updateMember(memberData)
    }
}