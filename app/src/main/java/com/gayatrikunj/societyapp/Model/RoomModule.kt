package com.gayatrikunj.societyapp.Model

import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class RoomModule @Inject constructor()  {
@Provides
    fun getDb(module: ThirdPartyModule):RoomDatabase{
        return module
    }
}