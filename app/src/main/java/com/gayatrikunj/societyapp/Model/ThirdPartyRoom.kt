package com.gayatrikunj.societyapp.Model

import android.util.Log
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import javax.inject.Inject

class ThirdPartyModule @Inject constructor(): RoomDatabase() {
    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        Log.d("Checked::","createOpenHelper")
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
        Log.d("Checked::","createInvalidationTracker")
    }

    override fun clearAllTables() {
        Log.d("Checked::","clearAllTables")
        TODO("Not yet implemented")
    }


}