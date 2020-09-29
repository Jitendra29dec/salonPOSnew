package com.hubwallet.apptspos.calander.book_multiple_appointment

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.Localdatabase.Servicedatasa
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.CommonUtils
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistDatum
import com.hubwallet.apptspos.calander.add_deposit.OnTextViewClickListener
import com.hubwallet.apptspos.employes.service.ServiceDetails
import kotlinx.android.synthetic.main.multiple_appointment.view.*

class MultipleAppointmentAdapter(var context: Context, var serviceList: ArrayList<ServiceDetails>,
                                 var stylestList: ArrayList<StylistDatum>, var saveservicesList:ArrayList<Servicedatasa>,var listener: OnTextViewClickListener) : RecyclerView.Adapter<MultipleAppointmentAdapter.ViewHolder>() {

    val timeList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.multiple_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.spnService.onItemSelectedListener = null
        holder.itemView.spnStylest.onItemSelectedListener = null
        val adapter = ArrayAdapter<ServiceDetails>(context, android.R.layout.simple_spinner_item,
                serviceList)

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        holder.itemView.spnService.adapter = adapter

        val stylestAdapter = ArrayAdapter<StylistDatum>(context, android.R.layout.simple_spinner_item,
                stylestList)

        holder.itemView.spnService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                holder.itemView.priceTextView.text = serviceList[position].price.toString()
                holder.itemView.durationEdt.text = serviceList[position].duration.toString()
                listener.onItemSelectListener(1, position, holder.adapterPosition)
            }

        }

        stylestAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        holder.itemView.spnStylest.adapter = stylestAdapter

        holder.itemView.spnStylest.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                listener.onItemSelectListener(2, position, holder.adapterPosition)
            }

        }
        holder.itemView.timeLayout.setOnClickListener {
            listener.clickHandle(1, position)
        }
        holder.itemView.removeImg.setOnClickListener {
            listener.remove(position)
        }

        holder.itemView.plusTv.setOnClickListener {
            val duration = holder.itemView.durationEdt.text.toString()
            if (duration.isNotEmpty()) {
                var durationInt = duration.toInt()
                durationInt += 1
                holder.itemView.durationEdt.text = durationInt.toString()
                listener.onDurationChange(position, durationInt.toString())
            }
        }
        holder.itemView.minusTv.setOnClickListener {
            val duration = holder.itemView.durationEdt.text.toString()
            if (duration.isNotEmpty()) {
                var durationInt = duration.toInt()
                if (durationInt >= 1) {
                    durationInt -= 1
                    holder.itemView.durationEdt.text = durationInt.toString()
                    listener.onDurationChange(position, durationInt.toString())
                }
            }
        }
        if (timeList[position].isNotEmpty()) {
            holder.itemView.timeEdt.text = CommonUtils.change24to12hours(timeList[position])

        } else {
            holder.itemView.timeEdt.text = ""
        }
        holder.itemView.pointEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}