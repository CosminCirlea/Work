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

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.TourActivity;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TourBookingsAdapter extends
    RecyclerView.Adapter<TourBookingsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TourBookingManager> mTours;

    public TourBookingsAdapter(Context context, ArrayList<TourBookingManager> tours) {
        mTours = tours;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBuyerName, tvTitle, tvBookDay, tvIncome;
        private Button mAcceptBook, mDenyBook;
        public ViewHolder(View itemView) {
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
        String buyerName = mTours.get(position).getmBuyerName();
        String bookDay = mTours.get(position).getmBookingDates();
        Double income = mTours.get(position).getmPrice();

        viewHolder.tvTitle.setText(tourTitle);
        viewHolder.tvBuyerName.setText(buyerName);
        viewHolder.tvIncome.setText(income.toString());
        viewHolder.tvBookDay.setText(bookDay);

        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, TourActivity.class);
                String serializedTour = new Gson().toJson(mTours.get(position));
                mIntent.putExtra(NavigationConstants.TOUR_MODEL_KEY,serializedTour);
                mContext.startActivity(mIntent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}