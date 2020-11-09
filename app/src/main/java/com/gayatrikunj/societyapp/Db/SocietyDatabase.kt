package com.gayatrikunj.societyapp.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.TransactionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [MemberData::class,TransactionModel::class],
    version = 1, exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class SocietyDatabase : RoomDatabase() {

    abstract fun societyDao(): SocietyDao

    companion object {
        @Volatile
        private var instance: SocietyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SocietyDatabase::class.java, "societyDatabase.db"
            )
                .addCallback(callback)
//                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2)
                .build()

        val MIGRATION_1_2 = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `transactionData` (`memberId` INTEGER NOT NULL, `item` TEXT NOT NULL, 'credit_amount' REAL NOT NULL, 'wallet_amount' REAL NOT NULL, 'debit_amount' REAL NOT NULL, 'date' TEXT NOT NULL,'type' TEXT NOT NULL," +
                        " PRIMARY KEY(`memberId`))")
            }
        }

        val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
//                CoroutineScope(Dispatchers.IO).launch{
//                    var list:ArrayList<MemberData> = getInsertingData()
//                    for(i in list){
//                        var data = MemberData()
//                        data.name = i.name
//                        data.address = i.address
//                        data.date = i.date
//                        data.expenseAmount = i.expenseAmount
//                        instance!!.societyDao().insertUsers(data)
//                    }
//                }
            }

            fun getInsertingData():ArrayList<MemberData>{
                var list = ArrayList<MemberData>()
                var data = MemberData()
                data.name = "Ashok Shah"
                data.address = "First floor (Front side)"
                list.add(data)
                var data1 = MemberData()
                data1.name = "Ajit Bhatnagar"
                data1.address = "Second floor (Front side)"
                list.add(data1)
                var data2 = MemberData()
                data2.name = "Dinesh Rustogi"
                data2.address = "Third floor (Front side)"
                list.add(data2)
                var data3 = MemberData()
                data3.name = "Aditya Tyagi"
                data3.address = "Fourth floor (Front side)"
                list.add(data3)
                var data4 = MemberData()
                data4.name = "Pankaj Belwal (Kamala)"
                data4.address = "First floor (Back side)"
                list.add(data4)
                var data5 = MemberData()
                data5.name = "Rajiv Tyagi"
                data5.address = "Second floor (Back side)"
                list.add(data5)
                var data6 = MemberData()
                data6.name = "Sukhbir Singh"
                data6.address = "Third floor (Back side)"
                list.add(data6)
                var data7 = MemberData()
                data7.name = "Hira"
                data7.address = "Fourth floor (Back side)"
                list.add(data7)
                return list
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
            }

        }

    }


}