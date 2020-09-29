package com.hubwallet.apptspos.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.disscount_res.service.ServiceDicountDetails
import kotlinx.android.synthetic.main.checkout_product_item.view.*
import kotlinx.android.synthetic.main.checkout_product_item.view.afterDiscount
import kotlinx.android.synthetic.main.checkout_product_item.view.finalAmountTv
import kotlinx.android.synthetic.main.checkout_product_item.view.spnDisscount
import kotlinx.android.synthetic.main.checkout_product_item.view.taxTv
import kotlinx.android.synthetic.main.checkout_service_view.view.*


class ProductAdapter(var context: Context, var list: ArrayList<Product>, var productDisscuntList: ArrayList<ServiceDicountDetails>, var removeListener: RemoveListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.checkout_product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceQty = list[position].price_retail * list[position].quantity
        list[position].priceWithQty = priceQty
        val product = list[position]
        holder.itemView.itemTv.text = product.product_name
        holder.itemView.barcode.text = product.barcode_id
        holder.itemView.sku.text = product.sku
        holder.itemView.perPrice.text = product.price_retail.toString()
        holder.itemView.qty.text = product.quantity.toString()

        holder.itemView.price.text = product.priceWithQty.toString()
//        holder.itemView.discount.text = product.cart_id
        holder.itemView.taxTv.text = product.tax_amount.toString()
        holder.itemView.finalAmountTv.text = product.finalAmount.toString()
        holder.itemView.afterDiscount.text = product.afterDisscunt.toString()
        val adapter = ArrayAdapter<ServiceDicountDetails>(context, android.R.layout.simple_spinner_item,
                productDisscuntList)
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        holder.itemView.spnDisscount.adapter = adapter


        holder.itemView.spnDisscount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                list[position].selectedDisscountPosition = i
                if (i > 0) {
                    var disscount = 0.0
                    val serviceDicountDetails = productDisscuntList[holder.itemView.spnDisscount.selectedItemPosition]
                    if (serviceDicountDetails.discountType == "%") {
                        disscount = (product.priceWithQty * serviceDicountDetails.discount.toDouble()) / 100
                    } else {
                        disscount = serviceDicountDetails.discount.toDouble()
                    }
                    val total = product.priceWithQty + product.tax_amount - disscount
                    holder.itemView.finalAmountTv.text = "$total"

                    val afterDiscountPrice = product.priceWithQty - disscount
                    holder.itemView.afterDiscount.text = "$afterDiscountPrice"
                    removeListener.onSelectionChange("product", position, total, disscount, afterDiscountPrice, i)
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }
        if (holder.itemView.spnDisscount.selectedItemPosition > 0) {
            var disscount = 0.0
            val serviceDicountDetails = productDisscuntList[holder.itemView.spnDisscount.selectedItemPosition]
            if (serviceDicountDetails.discountType == "%") {
                disscount = (product.priceWithQty * serviceDicountDetails.discount.toDouble()) / 100
            } else {
                disscount = serviceDicountDetails.discount.toDouble()
            }
            val total = product.priceWithQty + product.tax_amount - disscount
            holder.itemView.finalAmountTv.text = "$total"
            val afterDiscountPrice = product.priceWithQty - disscount
            holder.itemView.afterDiscount.text = "$afterDiscountPrice"
        } else {
            val total = product.priceWithQty + product.tax_amount
            holder.itemView.finalAmountTv.text = "$total"

        }

        holder.itemView.deleteTv.setOnClickListener {
            removeListener.onRemove(product.product_id, "product", position)
        }
    }
}