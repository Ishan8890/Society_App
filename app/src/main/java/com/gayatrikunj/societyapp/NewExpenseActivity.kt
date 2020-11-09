package com.gayatrikunj.societyapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gayatrikunj.societyapp.Db.LocalDateConverter
import com.gayatrikunj.societyapp.Db.SocietyDatabase
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import com.gayatrikunj.societyapp.Model.BottomMessageBar
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.SocietyViewModel
import com.gayatrikunj.societyapp.Model.SocietyViewModelFactory
import kotlinx.android.synthetic.main.member_ui.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewExpenseActivity : AppCompatActivity() {

    var perHead: Float = 0.0f
    lateinit var items: Array<String>
    var selectedItem: String = ""
    lateinit var societyViewModel: SocietyViewModel
    var reason_Others: Boolean = false
    var reason:String=""

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.member_ui)
        var dao = SocietyDatabase.invoke(this).societyDao()
        var repositary = SocietyRepositary(dao)
        var factory = SocietyViewModelFactory(repositary)

        societyViewModel = ViewModelProvider(this, factory).get(SocietyViewModel::class.java)

        items = resources.getStringArray(R.array.itemList)

        if (expensesItems != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, items
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            expensesItems.adapter = adapter
        }

        events()

    }

    fun events() {
        expensesItems.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0, 1, 2, 3 -> {
                        others.visibility = View.GONE
                        selectedItem = items[position]
                        reason_Others = false
                    }
                    4 -> {
                        others.visibility = View.VISIBLE
                        if (others.text.toString() != null || !others.text.isEmpty()) {
                            reason_Others = true
                            selectedItem = items[position]
                        } else {
                            Toast.makeText(
                                this@NewExpenseActivity,
                                "Reason field is Empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        fun isValidDate(inDate: String): Boolean {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            dateFormat.setLenient(false)
            try {
                dateFormat.parse(inDate.trim { it <= ' ' })
            } catch (pe: ParseException) {
                return false
            }
            return true
        }

        button2.setOnClickListener() {
            var dateInput: String = calender_date.text.toString()
            var expense = expense_amount.text.toString()
            LocalDateConverter.hideKeyBoard(this,this@NewExpenseActivity)
            if (reason_Others) {
                reason = others.text.toString()
            }
            if (expense != null && !expense.isEmpty() && perHead != null && selectedItem != null && !selectedItem.isEmpty()) {
                if (isValidDate(dateInput)) {
                    var date: Date? = LocalDateConverter.stringToDate(dateInput)
                    var calender: Calendar = Calendar.getInstance();
                    calender.setTime(date);
                    var month: Int = calender.get(Calendar.MONTH)
                    var list: ArrayList<MemberData> =
                        InsertMember.getInsertingData(
                            dateInput,
                            expense,
                            perHead,
                            selectedItem,
                            month,
                            reason
                        )
                    societyViewModel.insert(list)

                    societyViewModel.statMsg.observe(this, androidx.lifecycle.Observer {
                        Log.d("", "Message:: " + it)
                        BottomMessageBar.snackBar(button2, it)
                        expense_amount.text.clear()
                        per_head.text = getString(R.string.expense_head)
                        expensesItems.setSelection(0)
                        calender_date.text = getString(R.string.press_the_calender_icon_to_set_the_date)
                    })
                } else {
                    BottomMessageBar.snackBar(button2, "Select Calender date.")
                }
            } else {
                BottomMessageBar.snackBar(button2, "Field is Empty")
            }
        }

        calender.setOnClickListener {
            var calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(
                    this@NewExpenseActivity, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        calender_date.text =
                            (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    }, year, month, day
                )

            datePickerDialog.show()
        }

        expense_amount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (expense_amount.text.toString().length > 0)
                    if (!s.isEmpty() || s.toString() != null) {
                        var floatNum = s.toString().toFloat()
                        perHead = floatNum / 8
                        per_head.text = perHead.toString() + " per/head"
                        Log.d(">>>>>>>>>>", perHead.toString())
                    }
            }
        })
    }
}
