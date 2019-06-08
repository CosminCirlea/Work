package com.example.worldapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.ParkingActivity;
import com.example.worldapp.Activities.TourActivity;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.ParkingCore;
import com.example.worldapp.Models.ParkingModel;
import com.example.worldapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyParkingsAdapter extends
        RecyclerView.Adapter<MyParkingsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ParkingModel> mParkings;

    public MyParkingsAdapter(Context context, ArrayList<ParkingModel> parkings) {
        mParkings = parkings;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.template_row_my_parkings, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String title = mParkings.get(position).getmTitle();
        String location = mParkings.get(position).getmAddress();
        double pricePerDay = mParkings.get(position).getmPricePerDay();
        String price = pricePerDay +" EUR/day";

        viewHolder.tvAnnouncementTitle.setText(title);
        viewHolder.tvLocation.setText(location);
        viewHolder.tvPrice.setText(price);
        viewHolder.tvType.setText(mParkings.get(position).getmType());
        viewHolder.tvAvailability.setText(Integer.toString(mParkings.get(position).getmSpotsNumber()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ParkingActivity.class);
                String serializedParking = new Gson().toJson(mParkings.get(position));
                mIntent.putExtra(NavigationConstants.PARKING_MODEL_KEY, serializedParking);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mParkings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnnouncementTitle, tvLocation, tvAvailability, tvType, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvAnnouncementTitle = itemView.findViewById(R.id.tv_parking_announcement_title);
            tvLocation = itemView.findViewById(R.id.tv_parking_location);
            tvType = itemView.findViewById(R.id.tv_parking_type);
            tvAvailability = itemView.findViewById(R.id.tv_parking_availability);
            tvPrice = itemView.findViewById(R.id.tv_parking_price);
        }
    }
}


