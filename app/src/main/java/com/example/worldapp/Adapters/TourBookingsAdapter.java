package com.example.worldapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.TourActivity;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Interfaces.ClickListener;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourBookingsAdapter extends
    RecyclerView.Adapter<TourBookingsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TourBookingManager> mTours;
    private DatabaseReference mBookingDatabase;
    private Button mAcceptBook, mDenyBook;
    private GuidedToursModel mTour;
    ArrayList<String> mExistingBookedTours = new ArrayList<>();

    public TourBookingsAdapter(Context context, ArrayList<TourBookingManager> tours) {
        mTours = tours;
        mContext = context;
        mBookingDatabase = FirebaseDatabase.getInstance().getReference().child("BookingManager");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvBuyerName, tvTitle, tvBookDay, tvIncome;

        public ViewHolder(final View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvBuyerName = itemView.findViewById(R.id.tv_notifications_buyer_name);
            tvTitle = itemView.findViewById(R.id.tv_notifications_tour_title);
            tvBookDay = itemView.findViewById(R.id.tv_notifications_booking_day);
            tvIncome = itemView.findViewById(R.id.tv_notifications_price_amount);
            mAcceptBook = itemView.findViewById(R.id.btn_accept_booking);
            mDenyBook = itemView.findViewById(R.id.btn_deny_booking);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.template_row_buy_notifications, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String tourTitle= mTours.get(position).getmAnnouncementTitle();
        final String buyerName = mTours.get(position).getmBuyerName();
        final String bookDay = mTours.get(position).getmBookingDates();
        final Double income = mTours.get(position).getmPrice();
        final String bookID = mTours.get(position).getmBookingId();
        final String tourId = mTours.get(position).getmAnnouncementId();

        viewHolder.tvTitle.setText(tourTitle);
        viewHolder.tvBuyerName.setText(buyerName);
        viewHolder.tvIncome.setText(income.toString());
        viewHolder.tvBookDay.setText(bookDay);
        mAcceptBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("mStatus", ConstantValues.BOOKING_ACCEPTED);
                mBookingDatabase.child(bookID).updateChildren(map);
                updateTourBookingDate(tourId, bookDay);
            }
        });

        mDenyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("mStatus", ConstantValues.BOOKING_DENIED);
                mBookingDatabase.child(bookID).updateChildren(map);
            }
        });
    }

    private void updateTourBookingDate(String mTourId, String mDate)
    {
        mTour = new GuidedToursModel();
        if (mTour.getmBookedDates()!=null) {
            mExistingBookedTours = mTour.getmBookedDates();
        }
        mExistingBookedTours.addAll(mTour.getmBookedDates());
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mDate);
        FirebaseHelper.Instance().mToursDatabaseReference.child(mTourId).updateChildren(map);
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}