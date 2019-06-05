package com.example.worldapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TourBookingsAdapter extends
    RecyclerView.Adapter<TourBookingsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TourBookingManager> mBookingManager;
    private DatabaseReference mBookingDatabase, mTourDatabase, mAccommodationDatabase;
    private Button mAcceptBook, mDenyBook;
    private GuidedToursModel mTour;
    ArrayList<String> mExistingBookedTours = new ArrayList<>();

    public TourBookingsAdapter(Context context, ArrayList<TourBookingManager> tours) {
        mBookingManager = tours;
        mContext = context;
        mBookingDatabase = FirebaseDatabase.getInstance().getReference().child("BookingManager");
        mTourDatabase = FirebaseDatabase.getInstance().getReference().child("Tours");
        mAccommodationDatabase = FirebaseHelper.mAccommodationDatabaseReference;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvBuyerName, tvTitle, tvBookDay, tvIncome, tvBuyerPhone, tvBookDayText, tvScheduleText, tvSchedule;

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
            tvBuyerPhone = itemView.findViewById(R.id.tv_notifications_phone_number);
            tvBookDayText =itemView.findViewById(R.id.tv_notifications_booking_day_text);
            tvScheduleText =itemView.findViewById(R.id.tv_notifications_schedule_text);
            tvSchedule =itemView.findViewById(R.id.tv_notifications_schedule_day);
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
       /* String tourTitle= mBookingManager.get(position).getmAnnouncementTitle();
        final String buyerName = mBookingManager.get(position).getmBuyerName();
        final String bookDay = mBookingManager.get(position).getmBookingDates();
        final Double income = mBookingManager.get(position).getmPrice();
        final String bookID = mBookingManager.get(position).getmBookingId();
        final String itemId = mBookingManager.get(position).getmAnnouncementId();
        String buyerPhone = mBookingManager.get(position).getmBuyerPhone();

        viewHolder.tvTitle.setText(tourTitle);
        viewHolder.tvBuyerName.setText(buyerName);
        viewHolder.tvIncome.setText(income.toString()+" EUR");
        viewHolder.tvBookDay.setText(bookDay);
        viewHolder.tvBuyerPhone.setText(buyerPhone);*/
        final String bookID = mBookingManager.get(position).getmBookingId();
        final String itemId = mBookingManager.get(position).getmAnnouncementId();

        if (mBookingManager.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_TOUR) {
            String tourTitle = mBookingManager.get(position).getmAnnouncementTitle();
            final String bookingDate = mBookingManager.get(position).getmBookingDates();
            Double income = mBookingManager.get(position).getmTotalPrice();
            String phoneNumber = mBookingManager.get(position).getmOwnerPhone();
            String schedule = mBookingManager.get(position).getmSchedule();

            viewHolder.tvScheduleText.setText("Schedule: ");
            viewHolder.tvBookDayText.setText("Booking day :");
            viewHolder.tvTitle.setText(tourTitle);
            viewHolder.tvBookDay.setText(bookingDate);
            viewHolder.tvIncome.setText(income.toString() + " EUR");
            viewHolder.tvBuyerPhone.setText(phoneNumber);
            viewHolder.tvSchedule.setText(schedule);

            mAcceptBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("mStatus", ConstantValues.BOOKING_ACCEPTED);
                    mBookingDatabase.child(bookID).updateChildren(map);
                    if (bookingDate !=null && itemId != null) {
                        HashMap<String, Object> auxMap = new HashMap<>();
                        auxMap.put("mBookedDates", bookingDate );
                        //updateTourBookingDate(itemId, bookDay);
                        FirebaseHelper.Instance().updateTourBookedDates(itemId, bookingDate);
                        //mTourDatabase.child(itemId).child("mBookedDates").updateChildren(auxMap);
                    }
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
        else if (mBookingManager.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_ACCOMMODATION)
        {
            TourBookingManager mHome = mBookingManager.get(position);
            String title = mHome.getmAnnouncementTitle();
            final String startDate = mHome.getmStartDate();
            String endDate = mHome.getmEndDate();
            String price = Double.toString(mHome.getmPrice());
            String phone = mHome.getmOwnerPhone();

            viewHolder.tvScheduleText.setText("End date: ");
            viewHolder.tvBookDayText.setText("Start date :");
            viewHolder.tvTitle.setText(title);
            viewHolder.tvBookDay.setText(startDate);
            viewHolder.tvIncome.setText(price + " EUR");
            viewHolder.tvBuyerPhone.setText(phone);
            viewHolder.tvSchedule.setText(endDate);

            mAcceptBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("mStatus", ConstantValues.BOOKING_ACCEPTED);
                    mBookingDatabase.child(bookID).updateChildren(map);
                    if (startDate !=null && itemId != null) {
                        HashMap<String, Object> auxMap = new HashMap<>();
                        auxMap.put("mBookedDates", startDate );
                        //updateTourBookingDate(itemId, bookDay);
                        //FirebaseHelper.Instance().updateTourBookedDates(itemId, startDate);
                        //mTourDatabase.child(itemId).child("mBookedDates").updateChildren(auxMap);
                    }
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


    }


    private void updateTourBookingDate(final String mTourId, String mDate)
    {
        mTourDatabase = FirebaseDatabase.getInstance().getReference().child("Tours");
        mTourDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    GuidedToursModel mAuxTour = data.getValue(GuidedToursModel.class);
                    if (mAuxTour.getmTourId().contains(mTourId))
                    {
                        mTour = mAuxTour;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (mTour.getmBookedDates()!=null) {
            mExistingBookedTours = mTour.getmBookedDates();
        }
        mExistingBookedTours.add(mDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedTours);
        FirebaseHelper.Instance().mToursDatabaseReference.child(mTourId).updateChildren(map);
    }

    @Override
    public int getItemCount() {
        return mBookingManager.size();
    }

}