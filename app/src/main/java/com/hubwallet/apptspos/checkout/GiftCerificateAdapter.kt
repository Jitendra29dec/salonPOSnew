package com.hubwallet.apptspos.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.checkout_gift_cer_item.view.*
import kotlinx.android.synthetic.main.checkout_gift_cer_item.view.finalAmountTv

class GiftCerificateAdapter(var context: Context, var list: ArrayList<Certificate>, var removeListener: RemoveListener) : RecyclerView.Adapter<GiftCerificateAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.checkout_gift_cer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        val number = position + 1
        holder.itemView.number.text = "$number"
        holder.itemView.serviceName.text = product.sender_name
        holder.itemView.certificateTextView.text = product.gift_certificate_no
        holder.itemView.finalAmountTv.text = product.amount.toString()
        holder.itemView.deleteTv.setOnClickListener {
            removeListener.onRemove(product.gift_id, "certificate", position)
        }
    }
}