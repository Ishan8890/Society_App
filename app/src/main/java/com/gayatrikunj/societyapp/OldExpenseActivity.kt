package com.gayatrikunj.societyapp

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gayatrikunj.societyapp.Adapter.SocietyAdapter
import com.gayatrikunj.societyapp.Db.LocalDateConverter
import com.gayatrikunj.societyapp.Db.SocietyDatabase
import com.gayatrikunj.societyapp.Db.SocietyRepositary
import com.gayatrikunj.societyapp.Model.*
import kotlinx.android.synthetic.main.activity_new_expense.*
import kotlinx.android.synthetic.main.activity_new_expense.button2
import kotlinx.android.synthetic.main.activity_new_expense.recyclerView
import kotlinx.android.synthetic.main.member_ui.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OldExpenseActivity : AppCompatActivity(), ButtonStatus {

    lateinit var societyViewModel: SocietyViewModel
    lateinit var diningAdapter: SocietyAdapter
    lateinit var updatedMemberList: List<MemberData>
    var perHead: Float = 0.0f
    lateinit var items: Array<String>
    var selectedItem: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        var dao = SocietyDatabase.invoke(this).societyDao()
        var repositary = SocietyRepositary(dao)
        var factory = SocietyViewModelFactory(repositary)

        societyViewModel = ViewModelProvider(this, factory).get(SocietyViewModel::class.java)
        items = resources.getStringArray(R.array.itemList)

        if (oldExpensesItems != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, items
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            oldExpensesItems.adapter = adapter
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        updatedMemberList = ArrayList()

        events()


    }

    override fun status(data: List<MemberData>, position: Int) {
        updatedMemberList = data
    }

    fun updateMemberData(
        date: String,
        expenseAmount: Float,
        perHead: Float,
        memberList: List<MemberData>
    ) {
        for (i in memberList) {
            var data = MemberData()
//            data.date = date
            data.expenseAmount = expenseAmount
            data.memberId = i.memberId
            data.name = i.name
            data.address = i.address
            data.isChecked = i.isChecked
            data.individualAmount = perHead
            societyViewModel.updateMember(data)
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

    fun events() {

        oldExpensesItems.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0, 1, 2, 3,4 -> {
                        selectedItem = items[position]
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        button2.setOnClickListener() {
            var dateInput: String = date.text.toString()
            if (isValidDate(dateInput)) {
                var date: Date? = LocalDateConverter.stringToDate(dateInput)
                var calender: Calendar = Calendar.getInstance();
                calender.setTime(date);
                var month: Int = calender.get(Calendar.MONTH)
                if (selectedItem != null && !selectedItem.isEmpty()) {
                    societyViewModel.getUserDate(month, selectedItem)
                        .observe(this, androidx.lifecycle.Observer {
                            if (it.size > 0){
                                recyclerView.visibility = View.VISIBLE
                                totalRecord.visibility = View.VISIBLE
                                var totalRecords:Int = it.size
                                totalRecord.text = "Total Records: ($totalRecords)"
                                diningAdapter = SocietyAdapter(it, this, this)
                                recyclerView!!.layoutManager = LinearLayoutManager(this)
                                recyclerView!!.adapter = diningAdapter
                                diningAdapter.notifyDataSetChanged()
                            }else{
                                recyclerView.visibility = View.INVISIBLE
                                totalRecord.visibility = View.VISIBLE
                                totalRecord.text = "No Records found."
                            }

                        })
                }
            } else {
                BottomMessageBar.snackBar(button2, "Select the date from Calender")
//                Toast.makeText(this, "Select the date from Calender", Toast.LENGTH_SHORT).show()
            }
        }
        readCalender.setOnClickListener {
            var calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(
                    this@OldExpenseActivity, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        date.text = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                        selectedDate.visibility = View.VISIBLE
                    }, year, month, day
                )
            datePickerDialog.show()
        }

    }
}
