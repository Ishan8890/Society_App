package com.gayatrikunj.societyapp.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gayatrikunj.societyapp.Db.SocietyRepositary

class SocietyViewModelFactory(private val repositary: SocietyRepositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SocietyViewModel::class.java)){
            return SocietyViewModel(repositary) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }
}