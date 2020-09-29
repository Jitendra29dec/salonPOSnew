package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.Gallery.PhotoData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyclerView extends RecyclerView.Adapter<HorizontalRecyclerView.HorizontalViewHolder> {

  //  private static final GroupAppointmentViewHolder R = ;
    private List<PhotoData> photoDataList;
    private Context context;
    private ApiCommunicator apiCommunicator;


    public HorizontalRecyclerView(Context context,List<PhotoData> uriArrayList,ApiCommunicator apiCommunicator) {
        this.photoDataList = uriArrayList;
        this.context = context;
        this.apiCommunicator =apiCommunicator;
    }


    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_image, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder horizontalViewHolder, int position) {
        PhotoData model = photoDataList.get(position);
        Glide.with(context)
                .load(photoDataList.get(position).getPhoto())
                .placeholder(R.drawable.place_holder_1)
                .error(R.drawable.place_holder_1)
                .into(horizontalViewHolder.mImageRecyclerView);
       /* Picasso.get()
                .load(photos.get(position))
                .into(horizontalViewHolder.mImageRecyclerView);*/
    }

    @Override
    public int getItemCount() {
        return photoDataList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageRecyclerView;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            mImageRecyclerView = itemView.findViewById(R.id.imgLoader);
        }
    }
}
