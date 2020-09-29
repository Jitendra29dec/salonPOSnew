package com.hubwallet.apptspos.employes.service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.employes.schedule.OnDayStatusChangeListener
import kotlinx.android.synthetic.main.emp_services_layout.view.*

class EmpServicesAdapter(var context: Context, var list: ArrayList<ServiceDetails>,var listener:OnDayStatusChangeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 222) {
            val view = LayoutInflater.from(context).inflate(R.layout.emp_services_title_hader, parent, false)
        return TitleViewHolder(view)
        }
        val view = LayoutInflater.from(context).inflate(R.layout.emp_services_layout, parent, false)
        return ServiceiewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 222
        }
        return 333
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder){
            return
        }
        holder.itemView.priceEdt.setText(list[position-1].price)
        holder.itemView.durationEdt.setText(list[position-1].duration)
        holder.itemView.serviceCb.setText(list[position-1].serviceName)
        holder.itemView.serviceCb.setOnCheckedChangeListener(null)
        holder.itemView.serviceCb.isChecked=list[position-1].active
        holder.itemView.serviceCb.setOnCheckedChangeListener { buttonView, isChecked ->
            listener.onStatusChange(isChecked,position)
        }
    }
    class TitleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    class ServiceiewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}