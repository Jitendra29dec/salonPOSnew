package com.hubwallet.apptspos.checkout.add_cetificate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateData
import kotlinx.android.synthetic.main.tamplet_item.view.*

class TampletAdapter(var context: Context, var list: ArrayList<TemplateData>, var selectionListener: SelectionListener) : RecyclerView.Adapter<TampletAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tamplet_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list[position].isChecked) {
            holder.itemView.layoutParent.background = ContextCompat.getDrawable(context, R.drawable.green_border)
        } else {
            holder.itemView.layoutParent.background = ContextCompat.getDrawable(context, R.drawable.gray_border)
        }
        holder.itemView.layoutParent.setOnClickListener {
            selectionListener.onSelection(position)
        }
        Glide.with(context).load("https://booknpay.com/new/assets/img/template/${list[position].image}").into(holder.itemView.imgTamplet);
    }
}