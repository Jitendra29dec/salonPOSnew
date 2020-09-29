package com.hubwallet.apptspos.calander.add_deposit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.CommonUtils
import kotlinx.android.synthetic.main.date_time_aad_deposit.view.*
import kotlinx.android.synthetic.main.date_time_aad_deposit_remove.view.*

class AddDepositAdapter(var context: Context, var dateList: ArrayList<String>, var startTimeList: ArrayList<String>,
                        var endTimeList: ArrayList<String>, var listener: OnTextViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 222) {
            val view = LayoutInflater.from(context).inflate(R.layout.date_time_aad_deposit, parent, false)
            return ViewHolder(view)
        }

        val view = LayoutInflater.from(context).inflate(R.layout.date_time_aad_deposit_remove, parent, false)
        return ViewHolderRemove(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 222
        }
        return 333
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolderRemove) {
            holder.itemView.addDepositDateEdt.setOnClickListener {
                listener.clickHandle(1, position)
            }
            holder.itemView.addDepositStartTmeEdt.setOnClickListener {
                listener.clickHandle(2, position)
            }
            holder.itemView.addDepositEndTmeEdt.setOnClickListener {
                listener.clickHandle(3, position)
            }
            holder.itemView.addDepositDateEdt.text = dateList[position]
            if (startTimeList[position].isNotEmpty())
                holder.itemView.addDepositStartTmeEdt.text = CommonUtils.change24to12hours(startTimeList[position])
            if (endTimeList[position].isNotEmpty())
                holder.itemView.addDepositEndTmeEdt.text = CommonUtils.change24to12hours(endTimeList[position])
          holder.itemView.removeImg.setOnClickListener {
              listener.remove(position)
          }
            return
        }
        holder.itemView.dateTv.setOnClickListener {
            listener.clickHandle(1, position)
        }
        holder.itemView.startTimeTextView.setOnClickListener {
            listener.clickHandle(2, position)
        }
        holder.itemView.endTimeTextView.setOnClickListener {
            listener.clickHandle(3, position)
        }
        holder.itemView.dateTv.text = dateList[position]
        if (startTimeList[position].isNotEmpty())
            holder.itemView.startTimeTextView.text = CommonUtils.change24to12hours(startTimeList[position])
        if (endTimeList[position].isNotEmpty())
            holder.itemView.endTimeTextView.text = CommonUtils.change24to12hours(endTimeList[position])

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ViewHolderRemove(itemView: View) : RecyclerView.ViewHolder(itemView)

}