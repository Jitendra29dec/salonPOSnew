package com.hubwallet.apptspos.employes.gallery

import Gallerydetails
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.gallery_image.view.*

class GalleyAdapter(var context: Context, var list: List<Gallerydetails>) : RecyclerView.Adapter<GalleyAdapter.Gallery>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gallery {
        var view =LayoutInflater.from(context).inflate(R.layout.gallery_image,parent,false)
        return Gallery(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: Gallery, position: Int) {


        Glide.with(context).load(list.get(position).image).into(holder.imageView);

    }

    class Gallery(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var imageView =itemView.IVprofilegallery

    }



}