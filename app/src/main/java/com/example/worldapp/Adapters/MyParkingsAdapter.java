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
import com.example.worldapp.Activities.TourActivity;
import com.example.worldapp.Constants.NavigationConstants;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnnouncementTitle, tvLocation, tvTourLandmarks, tvMaxGuests, tvDuration, tvPricePerTour;
        private ImageView ivTourPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvAnnouncementTitle = itemView.findViewById(R.id.tv_announcement_title);
            tvLocation = itemView.findViewById(R.id.tv_tour_location);
            tvTourLandmarks = itemView.findViewById(R.id.tv_tour_landmarks);
            tvMaxGuests = itemView.findViewById(R.id.tv_tour_capacity);
            tvDuration = itemView.findViewById(R.id.tv_tour_duration);
            tvPricePerTour = itemView.findViewById(R.id.tv_price_per_tour);
            ivTourPicture = itemView.findViewById(R.id.iv_listed_tour);
        }
    }

    @NonNull
    @Override
    public MyParkingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        return new MyParkingsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.template_row_my_parkings, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyParkingsAdapter.ViewHolder viewHolder, final int position) {
        String tourTitle = mParkings.get(position).getmTitle();
        /*String location = mParkings.get(position).getmTourCountry() + ", " + mParkings.get(position).getmTourRegion() + ", " + mParkings.get(position).getmTourCity();
        String landmarks = mParkings.get(position).getmTourLandmarks();
        int maxParticipants = mParkings.get(position).getmTourMaxParticipants();
        String aux = String.valueOf(maxParticipants) + " participants - ";
        String tourDuration = mParkings.get(position).getmTourDuration();
        Double price = mParkings.get(position).getmTourPrice();
        Glide.with(viewHolder.ivTourPicture.getContext()).load(mParkings.get(position).getmTourImageUrl()).into(viewHolder.ivTourPicture);
        String userID = mParkings.get(position).getmUserId();*/

       /* viewHolder.tvAnnouncementTitle.setText(tourTitle);
        viewHolder.tvLocation.setText(location);
        viewHolder.tvTourLandmarks.setText(landmarks);
        viewHolder.tvDuration.setText(tourDuration + " hours");
        viewHolder.tvMaxGuests.setText(aux);
        viewHolder.tvPricePerTour.setText(price.toString() + "$");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, TourActivity.class);
                String serializedTour = new Gson().toJson(mParkings.get(position));
                mIntent.putExtra(NavigationConstants.PARKING_MODEL_KEY, serializedTour);
                mContext.startActivity(mIntent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mParkings.size();
    }
}


