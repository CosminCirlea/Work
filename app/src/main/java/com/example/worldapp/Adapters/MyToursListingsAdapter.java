package com.example.worldapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.worldapp.Activities.TourActivity;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.example.worldapp.Constants.NavigationConstants.TOUR_MODEL_KEY;

public class MyToursListingsAdapter extends
    RecyclerView.Adapter<MyToursListingsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GuidedToursModel> mTours;

    public MyToursListingsAdapter(Context context, ArrayList<GuidedToursModel> tours) {
        mTours = tours;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnnouncementTitle, tvLocation, tvTourLandmarks, tvMaxGuests, tvDuration, tvPricePerTour;
        private ImageView ivTourPicture;
        private RatingBar mRatingBar;

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
            mRatingBar = itemView.findViewById(R.id.rb_tour_rating);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.template_row_listed_tours, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String tourTitle= mTours.get(position).getmTourTitle();
        String location = mTours.get(position).getmTourCountry() +", "+mTours.get(position).getmTourRegion()+", "+mTours.get(position).getmTourCity();
        String landmarks = mTours.get(position).getmTourLandmarks();
        int maxParticipants = mTours.get(position).getmTourMaxParticipants();
        String aux = String.valueOf(maxParticipants) + " participants - ";
        String tourDuration = mTours.get(position).getmTourDuration();
        Double price = mTours.get(position).getmTourPrice();
        Glide.with(viewHolder.ivTourPicture.getContext()).load(mTours.get(position).getmTourImageUrl()).into(viewHolder.ivTourPicture);

        viewHolder.tvAnnouncementTitle.setText(tourTitle);
        viewHolder.tvLocation.setText(location);
        viewHolder.tvTourLandmarks.setText(landmarks);
        viewHolder.tvDuration.setText(tourDuration+" hours");
        viewHolder.tvMaxGuests.setText(aux);
        viewHolder.tvPricePerTour.setText(price.toString()+"$");
        viewHolder.mRatingBar.setRating(3.4f);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, TourActivity.class);
                String serializedTour = new Gson().toJson(mTours.get(position));
                mIntent.putExtra(NavigationConstants.TOUR_MODEL_KEY,serializedTour);
                mContext.startActivity(mIntent);
            }
        });
    }

    public void updateList(List<GuidedToursModel> newList)
    {
        mTours = new ArrayList<>();
        mTours.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}