package com.hubwallet.apptspos.employes.schedule

import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.schedule_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScheduleAdapter(var context: Context, var list: ArrayList<DaysDetailsSchedule>, var listiner: OnDayStatusChangeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 222) {
            val view = LayoutInflater.from(context).inflate(R.layout.schedule_header, parent, false)
            return HeaderViewHolder(view)
        }
        val view = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 222
        }
        return 333
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            return
        }
        holder.itemView.dayNameTv.text = list[position-1].days
        holder.itemView.fromTV.text = list[position-1].from
        holder.itemView.toTV.text = list[position-1].to
        holder.itemView.switchBtn.setOnCheckedChangeListener(null)
        holder.itemView.switchBtn.isChecked = list[position-1].active
        holder.itemView.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            listiner.onStatusChange(isChecked, position)
        }


        holder.itemView.fromTV.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                holder.itemView.fromTV.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        holder.itemView.toTV.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                holder.itemView.toTV.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

    }


    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}