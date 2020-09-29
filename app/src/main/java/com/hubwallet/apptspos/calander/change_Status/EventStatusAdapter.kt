package com.hubwallet.apptspos.calander.change_Status

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.status_item.view.*


class EventStatusAdapter(var context: Context, var list: ArrayList<GetData>, var statusChangeListener: StatusChangeListener) : RecyclerView.Adapter<EventStatusAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.status_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.statusTv.text = list[position].name
        val background: Drawable = holder.itemView.statusName.getBackground()
        if (background is ShapeDrawable) {
            val shapeDrawable = background;
            shapeDrawable.getPaint().setColor(Color.parseColor(list[position].colorCode))
        } else if (background is GradientDrawable) {
            val gradientDrawable = background;
            gradientDrawable.setColor(Color.parseColor(list[position].colorCode))
        }
        holder.itemView.parentView.setOnClickListener {

            statusChangeListener.onStatusChangListener(list[position].name.trim().toLowerCase(), list[position].colorId)
        }
    }
}