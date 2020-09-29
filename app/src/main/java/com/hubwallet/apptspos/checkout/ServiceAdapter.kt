package com.hubwallet.apptspos.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.disscount_res.service.ServiceDicountDetails
import kotlinx.android.synthetic.main.checkout_product_item.view.*
import kotlinx.android.synthetic.main.checkout_service_view.view.*
import kotlinx.android.synthetic.main.checkout_service_view.view.afterDiscount
import kotlinx.android.synthetic.main.checkout_service_view.view.finalAmountTv
import kotlinx.android.synthetic.main.checkout_service_view.view.spnDisscount
import kotlinx.android.synthetic.main.checkout_service_view.view.taxTv

class ServiceAdapter(var context: Context, var list: ArrayList<Service>, var disscountList: ArrayList<ServiceDicountDetails>, var removeListener: RemoveListener) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.checkout_service_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = list[position]
        holder.itemView.serviceNameTv.text = service.service_name
        holder.itemView.stylestNameTv.text = service.stylist_name
        holder.itemView.priceTv.text = service.price.toString()
//        holder.itemView.afterDiscount.text = service.tax_amount.toString()
        holder.itemView.taxTv.text = service.tax_amount.toString()
        holder.itemView.afterDiscount.text = service.afterDisscunt.toString()
        holder.itemView.spnDisscount.onItemSelectedListener = null
        val adapter = ArrayAdapter<ServiceDicountDetails>(context, android.R.layout.simple_spinner_item,
                disscountList)

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        holder.itemView.spnDisscount.adapter = adapter
        holder.itemView.spnDisscount.setSelection(service.selectedDisscountPosition)
        holder.itemView.spnDisscount.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                list[position].selectedDisscountPosition = i
                if (i > 0) {
                    var disscount = 0.0
                    val serviceDicountDetails = disscountList[holder.itemView.spnDisscount.selectedItemPosition]
                    if (serviceDicountDetails.discountType == "%") {
                        disscount = (service.price * serviceDicountDetails.discount.toDouble()) / 100
                    } else {
                        disscount = serviceDicountDetails.discount.toDouble()
                    }
                    val total = service.price + service.tax_amount - disscount
                    holder.itemView.finalAmountTv.text = "$total"
                    val afterDiscountPrice = service.price - disscount
                    holder.itemView.afterDiscount.text = "$afterDiscountPrice"
                    removeListener.onSelectionChange("service", position, total, disscount, afterDiscountPrice, i)
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }
        if (holder.itemView.spnDisscount.selectedItemPosition > 0) {
            var disscount = 0.0
            val serviceDicountDetails = disscountList[holder.itemView.spnDisscount.selectedItemPosition]
            if (serviceDicountDetails.discountType == "%") {
                disscount = (service.price * serviceDicountDetails.discount.toDouble()) / 100
            } else {
                disscount = serviceDicountDetails.discount.toDouble()
            }
            val total = service.price + service.tax_amount - disscount
            holder.itemView.finalAmountTv.text = "$total"
            val afterDiscountPrice = service.price - disscount
            holder.itemView.afterDiscount.text = "$afterDiscountPrice"
        } else {
            val total = service.price + service.tax_amount
            holder.itemView.finalAmountTv.text = "$total"

        }

        holder.itemView.removeImg.setOnClickListener {
            removeListener.onRemove(service.appointment_id, "service", position)
        }
    }
}