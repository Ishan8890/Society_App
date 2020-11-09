package com.gayatrikunj.societyapp

import android.app.Application
import com.gayatrikunj.societyapp.dagger.DaggerComponent
import com.gayatrikunj.societyapp.dagger.DaggerDaggerComponent

class MyApplication: Application() {
    lateinit var component: DaggerComponent
    override fun onCreate() {
        super.onCreate()
        component = getData()
    }

    fun getData():DaggerComponent  =
        DaggerDaggerComponent.create()

}