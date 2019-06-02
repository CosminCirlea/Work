package com.example.worldapp.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;

import java.util.ArrayList;

public class TourBookingsAdapterTrips extends
    RecyclerView.Adapter<TourBookingsAdapterTrips.ViewHolder> {

    private Context mContext;
    private ArrayList<TourBookingManager> mTours;

    public TourBookingsAdapterTrips(Context context, ArrayList<TourBookingManager> tours) {
        mTours = tours;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStatus, tvTitle, tvBookDay, tvPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvStatus = itemView.findViewById(R.id.tv_notifications_status);
            tvTitle = itemView.findViewById(R.id.tv_notifications_tour_title);
            tvBookDay = itemView.findViewById(R.id.tv_notifications_booking_day);
            tvPrice = itemView.findViewById(R.id.tv_notifications_price_amount);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.template_row_buy_trips, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String tourTitle= mTours.get(position).getmAnnouncementTitle();
        String bookingDate = mTours.get(position).getmBookingDates();
        Double income = mTours.get(position).getmTotalPrice();
        int status = mTours.get(position).getmStatus();

        viewHolder.tvTitle.setText(tourTitle);
        viewHolder.tvBookDay.setText(bookingDate);
        viewHolder.tvPrice.setText(income.toString());
        setStatusWhitelabel(status, viewHolder.tvStatus);
    }

    private void setStatusWhitelabel(int status, TextView statusTV)
    {
        if (status == ConstantValues.BOOKING_PENDING)
        {
            statusTV.setText("Pending");
            statusTV.setTextColor(ContextCompat.getColor(mContext, R.color.yellowColor));
        }
        if (status == ConstantValues.BOOKING_ACCEPTED)
        {
            statusTV.setText("Accepted");
            statusTV.setTextColor(ContextCompat.getColor(mContext, R.color.greenColor));
        }
        if (status == ConstantValues.BOOKING_DENIED)
        {
            statusTV.setText("Denied");
            statusTV.setTextColor(ContextCompat.getColor(mContext, R.color.redColor));
        }
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}