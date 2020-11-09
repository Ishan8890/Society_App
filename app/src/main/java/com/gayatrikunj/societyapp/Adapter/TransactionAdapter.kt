package com.gayatrikunj.societyapp.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.gayatrikunj.societyapp.Model.ButtonStatus
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.Model.TransactionModel
import com.gayatrikunj.societyapp.R
import com.google.android.material.color.MaterialColors.getColor
import kotlinx.android.synthetic.main.row_member.view.*
import kotlinx.android.synthetic.main.row_member_record.view.*
import kotlinx.android.synthetic.main.row_member_record.view.date
import kotlinx.android.synthetic.main.row_member_record.view.per_head
import kotlinx.android.synthetic.main.row_member_record.view.status
import kotlinx.android.synthetic.main.row_member_record.view.sub_title_text
import kotlinx.android.synthetic.main.row_member_record.view.title_text
import kotlinx.android.synthetic.main.row_transaction.view.*

class TransactionAdapter(
    private val transactionList: List<TransactionModel>,
    val context: Context
) :
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mode = view.mode
        val amountType = view.amountType
        val transactionDate = view.date
        val balance = view.remainingAmount

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_transaction, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mode.text = transactionList.get(position).item
        holder.balance.text = "Balance Amount Rs "+transactionList.get(position).wallet_amount.toString()
        holder.transactionDate.text = "Transaction Date "+transactionList.get(position).date
        if(transactionList.get(position).type.equals("CR")){
            holder.amountType.setTextColor(Color.GREEN)
            holder.amountType.text = "Credit Amount Rs "+"(+"+transactionList.get(position).credit_amount.toString()+")"
        }else{
            holder.amountType.setTextColor(Color.RED)
            holder.amountType.text = "Debit Amount Rs "+"(-"+transactionList.get(position).debit_amount.toString()+")"
        }

    }

    override fun getItemCount() = transactionList.size
}