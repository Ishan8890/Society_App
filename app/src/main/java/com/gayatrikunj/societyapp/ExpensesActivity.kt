package com.gayatrikunj.societyapp

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gayatrikunj.societyapp.Db.SocietyDatabase
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import com.gayatrikunj.societyapp.Model.SocietyViewModel
import com.gayatrikunj.societyapp.Model.SocietyViewModelFactory
import com.gayatrikunj.societyapp.Model.TransactionModel
import kotlinx.android.synthetic.main.activity_expenses.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ExpensesActivity : AppCompatActivity() {
    lateinit var societyViewModel: SocietyViewModel
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)
        var dao = SocietyDatabase.invoke(this).societyDao()
        var repositary = SocietyRepositary(dao)
        var factory = SocietyViewModelFactory(repositary)

        societyViewModel = ViewModelProvider(this, factory).get(SocietyViewModel::class.java)
        new_expenses.setOnClickListener {
            startActivity(Intent(this@ExpensesActivity, NewExpenseActivity::class.java))
        }

        old_expenses.setOnClickListener {
            startActivity(Intent(this@ExpensesActivity, OldExpenseActivity::class.java))
        }

        expense.setOnClickListener {
            startActivity(Intent(this@ExpensesActivity, TransactionActivity::class.java))
        }
        shareItems.setOnClickListener {

            societyViewModel.getTransactionHistory().observe(this, Observer {
                if(it.size>0){
                    createPdf(it)
                }
            })


        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun createPdf(transactionList:List<TransactionModel>){
        var pdfDocument = PdfDocument()
        var pageInfo:PdfDocument.PageInfo
        pageInfo = PdfDocument.PageInfo.Builder(300, 600,1).create();
        var page:PdfDocument.Page = pdfDocument.startPage(pageInfo)

//        page.getCanvas().drawText("Date: "+transactionList.get(0).date,30.0f, 40.0f, paint)
//        page.getCanvas().drawText("Items: "+transactionList.get(0).item,30.0f, 70.0f, paint)
//        page.getCanvas().drawText("Type: "+transactionList.get(0).type,30.0f, 100.0f, paint)
//        page.getCanvas().drawText("Balance: "+transactionList.get(0).wallet_amount,30.0f, 120.0f, paint)


//        var paint1:Paint = Paint()
//        page.getCanvas().drawText("Date: "+transactionList.get(0).date,30.0f, 40.0f, paint1)
//        page.getCanvas().drawText("Items: "+transactionList.get(0).item,30.0f, 70.0f, paint1)
//        page.getCanvas().drawText("Type: "+transactionList.get(0).type,30.0f, 100.0f, paint1)
//        page.getCanvas().drawText("Balance: "+transactionList.get(0).wallet_amount,30.0f, 120.0f, paint1)
        var paint:Paint = Paint()
        for ((index, value) in transactionList.withIndex()) {
            var length = (index+1)*40.0f

            println("the element at $index is $value  length-->$length")
            page.getCanvas().drawText("Date: "+value.date,30.0f, length, paint)
            page.getCanvas().drawText("Items: "+value.item,30.0f, length+20, paint)
            println("the element at $index is $value  length-->$length")
            page.getCanvas().drawText("Type: "+value.type,30.0f, length+30, paint)
            println("the element at $index is $value  length-->$length")
            page.getCanvas().drawText("Balance: "+value.wallet_amount,30.0f, length+40, paint)
//            page.getCanvas().drawText("-------------------------------------------------------------",30.0f, length+55, paint)
        }
//        for(i in transactionList){
//            var paint:Paint = Paint()
//            page.getCanvas().drawText("Date: "+i.date,10.0f, 65.0f, paint)
//            page.getCanvas().drawText("Items: "+i.item,10.0f, 45.0f, paint)
//            page.getCanvas().drawText("Type: "+i.type,10.0f, 65.0f, paint)
//            page.getCanvas().drawText("Balance: "+i.wallet_amount,10.0f, 85.0f, paint)
//        }
        pdfDocument.finishPage(page)

        var path:String = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
//            var filePath:String = Environment.getExternalStorageDirectory().getPath()+”/Download/”+editTextSerialNumberFetch.getText().toString()+”.pdf”;
//            var directory_path :String = Environment.getExternalStorageDirectory().getPath()+"/Download/";
        var file:File = File(path );
        if (!file.exists()) {
            file.mkdirs();
        }
        val targetPdf = path + "/society.pdf"
        val filePath = File(targetPdf)
        try {
            var outputStream:FileOutputStream = FileOutputStream(filePath)
            pdfDocument.writeTo(outputStream);
        } catch (e: IOException) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}
//for (i in array.indices) {
//    print(array[i])
//}