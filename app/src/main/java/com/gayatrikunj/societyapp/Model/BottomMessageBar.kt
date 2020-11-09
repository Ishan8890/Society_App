package com.gayatrikunj.societyapp.Model

import android.view.View
import com.google.android.material.snackbar.Snackbar

class BottomMessageBar {

    companion object{
        @JvmStatic
        fun snackBar(view:View,message:String){
            var snack= Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snack.show()
        }
    }
}