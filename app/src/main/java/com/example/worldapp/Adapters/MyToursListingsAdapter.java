package com.example.worldapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MyToursListingsAdapter extends
    RecyclerView.Adapter<MyToursListingsAdapter.ViewHolder> {

    private ArrayList<GuidedToursModel> mTours;

    public MyToursListingsAdapter(ArrayList<GuidedToursModel> tours) {
        mTours = tours;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnnouncementTitle, tvLocation, tvHouseType, tvMaxGuests, tvBeds, tvPricePerNight;

        public ViewHolder(View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvAnnouncementTitle = itemView.findViewById(R.id.tv_announcement_title);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvHouseType = itemView.findViewById(R.id.tv_venue_type);
            tvMaxGuests = itemView.findViewById(R.id.tv_guest_capacity);
            tvBeds = itemView.findViewById(R.id.tv_beds);
            tvPricePerNight = itemView.findViewById(R.id.tv_price_per_night);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.template_row_listed_homes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String tourTitle= mTours.get(position).getmTourTitle();
        String location = mTours.get(position).getmTourCountry() +", "+mTours.get(position).getmTourRegion()+", "+mTours.get(position).getmTourCity();
        String landmarks = mTours.get(position).getmTourLandmarks();
        int maxParticipants = mTours.get(position).getmTourMaxParticipants();
        String aux = String.valueOf(maxParticipants);
        String tourDuration = mTours.get(position).getmTourDuration();
        Double price = mTours.get(position).getmTourPrice();

        viewHolder.tvAnnouncementTitle.setText(tourTitle);
        viewHolder.tvLocation.setText(location);
        viewHolder.tvHouseType.setText(landmarks);
        viewHolder.tvBeds.setText(tourDuration+" hours");
        viewHolder.tvMaxGuests.setText(aux);
        viewHolder.tvAnnouncementTitle.setText(price.toString());
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}