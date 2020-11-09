package com.gayatrikunj.societyapp.Model


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import kotlinx.coroutines.launch


class SocietyViewModel(private val repositary: SocietyRepositary) : ViewModel() {

    var membersData = repositary.allMembers
    val statusMessage = MutableLiveData<String>()
    lateinit var message: String
    val statMsg: LiveData<String> get() = statusMessage

    fun insert(memberList: List<MemberData>) = viewModelScope.launch {
        val rowId = repositary.insert(memberList)
        if (rowId > 0) {
            statusMessage.value = "Record inserted Successfully."
        } else {
            statusMessage.value = "Record not inserted."
        }
        Log.d("insert data", rowId.toString())
    }

    fun insertWalletData(data: TransactionModel) = viewModelScope.launch {
        val rowId = repositary.insertWallet(data)
        if (rowId > 0) {
            statusMessage.value = "Amount added Successfully."
        } else {
            statusMessage.value = "Not inserted."
        }
    }


    fun updateMember(memberData: MemberData) = viewModelScope.launch {
        repositary.updateMember(memberData)
    }

    fun getUserDate(date: Int, reason: String): LiveData<List<MemberData>> {
        return repositary.getUserData(date, reason)
    }

//    fun getWalletAmount():LiveData<Float>{
//        return repositary.getValue()
//    }

    fun getTransactionHistory(): LiveData<List<TransactionModel>> {
        return repositary.getTransactionRecord()
    }

    fun getViewModel() {
        Log.d("", "Its Society view model")
    }

}


