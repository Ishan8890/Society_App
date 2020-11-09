package com.gayatrikunj.societyapp.dagger

import com.gayatrikunj.societyapp.MainActivity
import com.gayatrikunj.societyapp.Model.DaggerTestClass
import com.gayatrikunj.societyapp.Model.RoomModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface DaggerComponent {
//    fun getDaggger():DaggerTestClass
    fun inject(mainActivity: MainActivity)
}