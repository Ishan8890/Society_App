package com.gayatrikunj.societyapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gayatrikunj.societyapp.Adapter.TransactionAdapter
import com.gayatrikunj.societyapp.Db.LocalDateConverter
import com.gayatrikunj.societyapp.Db.SocietyDatabase
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import com.gayatrikunj.societyapp.Model.BottomMessageBar
import com.gayatrikunj.societyapp.Model.SocietyViewModel
import com.gayatrikunj.societyapp.Model.SocietyViewModelFactory
import com.gayatrikunj.societyapp.Model.TransactionModel
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.member_ui.*
import kotlinx.android.synthetic.main.wallet_ui.*

class TransactionActivity : AppCompatActivity() {
    lateinit var societyViewModel: SocietyViewModel
    lateinit var items: Array<String>
    var selectedItem: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        readWallet.setOnClickListener {
            transactionRecyclerView.visibility = View.INVISIBLE
            showDialog("")
        }

        var dao = SocietyDatabase.invoke(this).societyDao()
        var repositary = SocietyRepositary(dao)
        var factory = SocietyViewModelFactory(repositary)

        societyViewModel = ViewModelProvider(this, factory).get(SocietyViewModel::class.java)

        items = resources.getStringArray(R.array.ItemType)

        if (transactionSpinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, items
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            transactionSpinner.adapter = adapter
        }
        events()
    }

    fun events() {
        transactionSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0, 1, 2, 3 -> {
                        selectedItem = items[position]
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        transactionHistory.setOnClickListener {
            transactionRecyclerView.visibility = View.VISIBLE
            societyViewModel.getTransactionHistory().observe(this, Observer {
                var adapter = TransactionAdapter(it, this@TransactionActivity)
                transactionRecyclerView!!.layoutManager = LinearLayoutManager(this)
                transactionRecyclerView!!.adapter = adapter
                adapter.notifyDataSetChanged()
            })
        }

        transactionSubmit.setOnClickListener {
            transactionRecyclerView.visibility = View.INVISIBLE
            var expense = expenseAmount.text.toString()
            LocalDateConverter.hideKeyBoard(this,this@TransactionActivity)
            if (!selectedItem.isEmpty() && !expense.isEmpty()) {
                var expenseInNo = expense.toFloat()
                var date = LocalDateConverter.getCurrentDateTime()
                var data = TransactionModel()
                if (!date.isEmpty() && !expense.isEmpty())
                    data.date = date
                var remainingBalance = LocalDateConverter.getDataFromSharePref("Wallet_Amount", this)
                if (remainingBalance > expenseInNo) {
                    var updatedWalletBalance = remainingBalance - expenseInNo
                    data.wallet_amount = updatedWalletBalance
                    data.debit_amount = expenseInNo
                    data.item = selectedItem
                    data.type = "DR"
                    societyViewModel.insertWalletData(data)
                    transactionSpinner.setSelection(0)
                    expenseAmount.text.clear()
                    LocalDateConverter.setDataInSharedPref("Wallet_Amount",updatedWalletBalance,this)
//                    societyViewModel.statusMessage.observe(this, androidx.lifecycle.Observer {
//                        Log.d("", "Message:: " + it)
//                        BottomMessageBar.snackBar(expenseAmount, it)
//                        transactionSpinner.setSelection(0)
//                        expenseAmount.text.clear()
//                        LocalDateConverter.setDataInSharedPref("Wallet_Amount",updatedWalletBalance,this)
//                    })

                } else {
                    BottomMessageBar.snackBar(expenseAmount, "Insufficient Wallet balance.")
                }

            }else{
                BottomMessageBar.snackBar(expenseAmount, "Fields is Empty.")
            }

        }

    }


    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.wallet_ui)
        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
        val yesBtn = dialog.findViewById(R.id.dialogSubmit) as Button
        val walletEdit = dialog.findViewById(R.id.walletEdittext) as EditText
        val noBtn = dialog.findViewById(R.id.dismiss) as TextView
        yesBtn.setOnClickListener {
            var walletAmount = walletEdit.text.toString()
            var date = LocalDateConverter.getCurrentDateTime()
            var data = TransactionModel()
            if (!date.isEmpty() && !walletAmount.isEmpty()) {
                var floatAmount = walletAmount.toFloat()
                var remainingBalance = LocalDateConverter.getDataFromSharePref("Wallet_Amount", this)
                data.date = date
                data.credit_amount = floatAmount
                var updatedBalance = floatAmount + remainingBalance
                data.wallet_amount = updatedBalance
                data.item = "Wallet"
                data.type = "CR"
                societyViewModel.insertWalletData(data)
                LocalDateConverter.setDataInSharedPref("Wallet_Amount",updatedBalance,this)
                dialog.dismiss()
            } else {
                BottomMessageBar.snackBar(transactionSpinner, "Fields is Empty")
            }
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
}
