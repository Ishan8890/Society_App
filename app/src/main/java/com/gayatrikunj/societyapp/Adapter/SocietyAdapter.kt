package com.gayatrikunj.societyapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gayatrikunj.societyapp.Model.ButtonStatus
import com.gayatrikunj.societyapp.Model.MemberData
import com.gayatrikunj.societyapp.R
import kotlinx.android.synthetic.main.row_member.view.*
import kotlinx.android.synthetic.main.row_member_record.view.date
import kotlinx.android.synthetic.main.row_member_record.view.per_head
import kotlinx.android.synthetic.main.row_member_record.view.status
import kotlinx.android.synthetic.main.row_member_record.view.sub_title_text
import kotlinx.android.synthetic.main.row_member_record.view.title_text

class SocietyAdapter(
    private val memberList: List<MemberData>,
    val context: Context,
    var event: ButtonStatus
) :
    RecyclerView.Adapter<SocietyAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.title_text
        val address = view.sub_title_text
        val date = view.date
        val billStatus = view.status
        val individual_Amount = view.per_head
        val total_amount = view.totalAmount
        val paymentType = view.paymentType
        val reason = view.reason
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_member, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = memberList.get(position).name
        holder.address.text = memberList.get(position).address
        holder.date.text = memberList.get(position).date
        holder.total_amount.text = "Total Amount: "+memberList.get(position).expenseAmount
        holder.paymentType.text = "Payment Type: "+memberList.get(position).transactionType
        if(memberList.get(position).transactionType.equals("Others")){
            holder.reason.visibility = View.VISIBLE
            holder.reason.text = "Reason : "+memberList.get(position).reason
        }else{
            holder.reason.visibility = View.GONE
        }

        holder.individual_Amount.text = "Individual Amount: " + memberList.get(position).individualAmount

        if(memberList.get(position).isChecked){
            holder.billStatus.isChecked = memberList.get(position).isChecked
        }

        holder.billStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                memberList.get(position).isChecked = isChecked
            }else{
                memberList.get(position).isChecked = isChecked
            }
            event.status(memberList, position)
        }
    }

    override fun getItemCount() = memberList.size
}