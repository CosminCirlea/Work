package com.example.worldapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Models.BookingManager;
import com.example.worldapp.R;

import java.util.ArrayList;

public class TripsBookingAdapter extends
    RecyclerView.Adapter<TripsBookingAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BookingManager> mTours;

    public TripsBookingAdapter(Context context, ArrayList<BookingManager> tours) {
        mTours = tours;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStatus, tvTitle, tvBookDay, tvPrice, tvPhoneOwner, tvBookDayText, tvScheduleText, tvSchedule;
        public ViewHolder(View itemView) {
            super(itemView);
            InitializeViews(itemView);
        }

        private void InitializeViews(View itemView) {
            tvStatus = itemView.findViewById(R.id.tv_notifications_status);
            tvTitle = itemView.findViewById(R.id.tv_notifications_tour_title);
            tvBookDay = itemView.findViewById(R.id.tv_notifications_booking_day);
            tvPrice = itemView.findViewById(R.id.tv_notifications_price_amount);
            tvPhoneOwner =itemView.findViewById(R.id.tv_notifications_phone_number);
            tvBookDayText =itemView.findViewById(R.id.tv_notifications_booking_day_text);
            tvScheduleText =itemView.findViewById(R.id.tv_notifications_schedule_text);
            tvSchedule =itemView.findViewById(R.id.tv_notifications_schedule_day);
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
        if (mTours.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_TOUR) {
            String tourTitle = mTours.get(position).getmAnnouncementTitle();
            String bookingDate = mTours.get(position).getmBookingDates();
            Double income = mTours.get(position).getmTotalPrice();
            int status = mTours.get(position).getmStatus();
            String phoneNumber = mTours.get(position).getmOwnerPhone();
            String schedule = mTours.get(position).getmSchedule();

            viewHolder.tvScheduleText.setText("Schedule: ");
            viewHolder.tvBookDayText.setText("Booking day :");
            viewHolder.tvTitle.setText(tourTitle);
            viewHolder.tvBookDay.setText(bookingDate);
            viewHolder.tvPrice.setText(income.toString() + " EUR");
            viewHolder.tvPhoneOwner.setText(phoneNumber);
            viewHolder.tvSchedule.setText(schedule);
            setStatusWhitelabel(status, viewHolder.tvStatus);
        }
        else if (mTours.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_ACCOMMODATION)
        {
            BookingManager mHome = mTours.get(position);
            String title = mHome.getmAnnouncementTitle();
            String startDate = mHome.getmStartDate();
            String endDate = mHome.getmEndDate();
            String price = Double.toString(mHome.getmTotalPrice());
            String phone = mHome.getmOwnerPhone();
            int status = mHome.getmStatus();

            viewHolder.tvScheduleText.setText("End date: ");
            viewHolder.tvBookDayText.setText("Start date :");
            viewHolder.tvTitle.setText(title);
            viewHolder.tvBookDay.setText(startDate);
            viewHolder.tvPrice.setText(price + " EUR");
            viewHolder.tvPhoneOwner.setText(phone);
            viewHolder.tvSchedule.setText(endDate);
            setStatusWhitelabel(status, viewHolder.tvStatus);
        }
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
        if (status == ConstantValues.BOOKING_INCOMING)
        {
            statusTV.setText("Incoming");
            statusTV.setTextColor(ContextCompat.getColor(mContext, R.color.incomingColor));
        }
        if (status == ConstantValues.BOOKING_COMPLETED)
        {
            statusTV.setText("Denied");
            statusTV.setTextColor(ContextCompat.getColor(mContext, R.color.completedCOlor));
        }
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

}