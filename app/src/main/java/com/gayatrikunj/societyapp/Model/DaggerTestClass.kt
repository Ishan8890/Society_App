package com.gayatrikunj.societyapp.Model

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerTestClass @Inject constructor(transactionModel:TransactionModel){

    init {
        transactionModel.getModel()
//        societyModel.getViewModel()
    }

    fun getDagger(){
        Log.d("Check:::","Get dagger data")
    }
}