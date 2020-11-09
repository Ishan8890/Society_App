package com.gayatrikunj.societyapp.Db

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class LocalDateConverter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun stringToDate(str: String): Date?{
            var date:Date ?=null
            if(str!=null){
                val parsedDate: Date = SimpleDateFormat("dd/MM/yyyy").parse(str)
                date = parsedDate
            }
            return date
        }

        fun getCurrentDateTime():String {
            //above 26 Oreo
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val formatted = current.format(formatter)
//        println("Current Date and Time is: $formatted")
            val c = Calendar.getInstance()
            val dateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val datetime = dateformat.format(c.time)
            println(datetime)
            return datetime
        }
        private val sharedPrefFile = "kotlinsharedpreference"
        fun setDataInSharedPref(key:String,value:Float,context:Context){
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putFloat(key,value)
            editor.apply()
            editor.commit()
        }

        fun getDataFromSharePref(key:String,context:Context):Float{
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
               return sharedPreferences.getFloat(key,0.0f)
        }

        fun hideKeyBoard(context:Context,activity:Activity){
            val inputManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


}