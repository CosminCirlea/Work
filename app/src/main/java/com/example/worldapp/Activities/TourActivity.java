package com.example.worldapp.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.gson.Gson;

public class TourActivity extends BaseAppCompat {

    private ImageView mTourImage;
    private TextView mTitle, mLocation, mType, mDescription, mParticipants, mDuration, mPrice, mLandmarks;
    private RatingBar mRating;

    private GuidedToursModel mTour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        InitializeViews();
        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(NavigationConstants.TOUR_MODEL_KEY);
        mTour = gson.fromJson(aux, GuidedToursModel.class);
        SetValues();
    }

    private void SetValues()
    {
        Glide.with(mTourImage.getContext()).load(mTour.getmTourImageUrl()).into(mTourImage);
        mTitle.setText(mTour.getmTourTitle());
        mDescription.setText(mTour.getmTourDescription());
        String location = mTour.getmTourCountry()+","+mTour.getmTourRegion()+","+mTour.getmTourCity();
        mLocation.setText(location);
        mType.setText(mTour.getmTourType());
        mDescription.setText(mTour.getmTourDescription());
        mDuration.setText(mTour.getmTourDuration());
        mPrice.setText(mTour.getmTourPrice()+" $");
        mLandmarks.setText(mTour.getmTourLandmarks());
        mParticipants.setText(mTour.getmTourMaxParticipants()+"");
        mRating.setRating(3.4f);
    }

    private void InitializeViews()
    {
        mTourImage = findViewById(R.id.iv_tour_photo);
        mTitle = findViewById(R.id.tv_tour_title);
        mLocation = findViewById(R.id.tv_tour_location);
        mType = findViewById(R.id.tv_tour_type_details);
        mDescription = findViewById(R.id.tv_tour_description);
        mParticipants = findViewById(R.id.tv_tour_details_participants);
        mDuration = findViewById(R.id.tv_tour_details_duration);
        mPrice = findViewById(R.id.tv_tour_details_price);
        mLandmarks = findViewById(R.id.tv_tour_landmarks_details);
        mRating = findViewById(R.id.rb_tour_rating_details);
    }
}
