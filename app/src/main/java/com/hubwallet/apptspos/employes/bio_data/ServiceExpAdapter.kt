package com.hubwallet.apptspos.employes.bio_data

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.service_exp_item.view.*

class ServiceExpAdapter(var context: Context, var list: List<String>, var listiner: OnTextChangeListener) : RecyclerView.Adapter<ServiceExpAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.service_exp_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//
//        holder.itemView.edtService.addTextChangedListener(listener)
//
        holder.itemView.edtService.setText(list[position])
        holder.itemView.srNoTv.text = (position + 1).toString()
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listiner.onTextChange(s.toString(), position)

            }

        }
        holder.itemView.edtService.addTextChangedListener(listener)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}