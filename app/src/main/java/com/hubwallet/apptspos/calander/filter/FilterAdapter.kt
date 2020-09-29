package com.hubwallet.apptspos.calander.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.serach_item.view.*

class FilterAdapter(var context: Context, var list: ArrayList<SearchDeatails>, var searchListener: SearchListener) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.serach_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deatils=list[position]
        holder.itemView.textDetails.text="${deatils.customer_name} (${deatils.mobile_phone} ${deatils.appointment_date})"
        holder.itemView.textDetails.setOnClickListener { searchListener.onSearchClick(position) }
    }
}