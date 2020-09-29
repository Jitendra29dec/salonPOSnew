package com.hubwallet.apptspos.checkout.add_product

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.add_service.PurchageListener
import kotlinx.android.synthetic.main.checkout_add_product_item.view.*

class ProductAdapter(var context: Context, var list: ArrayList<Product>, var purchageListener: PurchageListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.checkout_add_product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.barcodeTxt.text = item.barcodeId
        holder.itemView.productName.text = item.productName
        holder.itemView.brandTv.text = item.brandName
        holder.itemView.productTypeTv.text = item.categoryName
        holder.itemView.price.text = item.priceRetail
        holder.itemView.editAvailableQty.text = item.quantity
        holder.itemView.barcodeTxt.text = item.barcodeId

        if (holder.textWatcher == null) {
            holder.itemView.purchgeQty.setText(item.purchageQuatity)
            holder.textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    purchageListener.onChange(p0.toString().trim(), position,list[position].productId )
                    list[position].purchageQuatity = p0.toString().trim()
                }

            }
            holder.itemView.purchgeQty.addTextChangedListener(holder.textWatcher)
        } else {
            holder.itemView.purchgeQty.removeTextChangedListener(holder.textWatcher)
            holder.itemView.purchgeQty.setText(item.purchageQuatity)
            holder.itemView.purchgeQty.addTextChangedListener(holder.textWatcher)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textWatcher: TextWatcher? = null

    }
}