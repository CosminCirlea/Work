package com.example.worldapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.worldapp.Models.BookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class InboxBookingsAdapter extends
    RecyclerView.Adapter<InboxBookingsAdapter.ViewHolder>  {
    private Context mContext;
    private ArrayList<BookingManager> mBookingManager;
    private DatabaseReference mBookingDatabase, mTourDatabase, mAccommodationDatabase, mParkingsDatabase;
    private Button mAcceptBook, mDenyBook;
    private GuidedToursModel mTour;
    ArrayList<String> mExistingBookedTours = new ArrayList<>();

    public InboxBookingsAdapter(Context context, ArrayList<BookingManager> tours) {
        mBookingManager = tours;
        mContext = context;
        mBookingDatabase = FirebaseDatabase.getInstance().getReference().child("BookingManager");
        mTourDatabase = FirebaseDatabase.getInstance().getReference().child("Tours");
        mAccommodationDatabase = FirebaseHelper.mAccommodationDatabaseReference;
        mParkingsDatabase = FirebaseHelper.mParkingsDatabaseReference;
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
        final String bookID = mBookingManager.get(position).getmBookingId();
        final String itemId = mBookingManager.get(position).getmAnnouncementId();

        if (mBookingManager.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_TOUR) {
            String tourTitle = mBookingManager.get(position).getmAnnouncementTitle();
            final String bookingDate = mBookingManager.get(position).getmBookingDates();
            Double income = mBookingManager.get(position).getmTotalPrice();
            String phoneNumber = mBookingManager.get(position).getmOwnerPhone();
            String schedule = mBookingManager.get(position).getmSchedule();
            String buyerName = mBookingManager.get(position).getmBuyerName();

            viewHolder.tvBuyerName.setText(buyerName);
            viewHolder.tvScheduleText.setText("Schedule: ");
            viewHolder.tvBookDayText.setText("Booking day :");
            viewHolder.tvTitle.setText(tourTitle);
            viewHolder.tvBookDay.setText(bookingDate);
            viewHolder.tvIncome.setText(income.toString() + " EUR");
            viewHolder.tvBuyerPhone.setText(phoneNumber);
            viewHolder.tvSchedule.setText(schedule);
            final ArrayList<GuidedToursModel> tours = new ArrayList<>();
            mAcceptBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("mStatus", ConstantValues.BOOKING_ACCEPTED);
                    mBookingDatabase.child(bookID).updateChildren(map);
                    if (bookingDate !=null && itemId != null) {
                        HashMap<String, Object> auxMap = new HashMap<>();
                        auxMap.put("mBookedDates", bookingDate );
                        FirebaseHelper.getTourById(itemId, bookingDate);
                        FirebaseHelper.incrementValueInTour(itemId, "mToursBookedNumber",1);
                        FirebaseHelper.incrementStatistics("Tours",mBookingManager.get(position).getmCountry(),1);
                    }
                }
            });

            mDenyBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(bookID);
                }
            });
        }
        else if (mBookingManager.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_ACCOMMODATION)
        {
            final BookingManager mHome = mBookingManager.get(position);
            String title = mHome.getmAnnouncementTitle();
            final String startDate = mHome.getmStartDate();
            String endDate = mHome.getmEndDate();
            final ArrayList<String> bookedDates = mHome.getmSelectedDates();
            String price = Double.toString(mHome.getmPrice());
            String phone = mHome.getmOwnerPhone();
            String buyerName = mBookingManager.get(position).getmBuyerName();

            viewHolder.tvBuyerName.setText(buyerName);
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
                    if (bookedDates !=null && itemId != null) {
                        HashMap<String, Object> auxMap = new HashMap<>();
                        auxMap.put("mBookedDates", bookedDates );
                        FirebaseHelper.getAccommodationById(itemId, bookedDates);
                        FirebaseHelper.incrementValueInAccommodation(itemId, "mBookedNumber",1);
                        FirebaseHelper.incrementStatistics("Accommodation",mHome.getmCountry(),1);
                    }
                }
            });

            mDenyBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(bookID);
                }
            });
        }

        else if (mBookingManager.get(position).getmManagerType() == ConstantValues.BOOKING_TYPE_PARKING)
        {
            final BookingManager mHome = mBookingManager.get(position);
            String title = mHome.getmAnnouncementTitle();
            final String startDate = mHome.getmStartDate();
            String endDate = mHome.getmEndDate();
            String price = Double.toString(mHome.getmPrice());
            String phone = mHome.getmOwnerPhone();
            String buyerName = mBookingManager.get(position).getmBuyerName();
            final ArrayList<String> bookedDates = mHome.getmSelectedDates();

            viewHolder.tvBuyerName.setText(buyerName);
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
                    if (bookedDates !=null && itemId != null) {
                        HashMap<String, Object> auxMap = new HashMap<>();
                        auxMap.put("mBookedDates", bookedDates );
                        FirebaseHelper.getParkingByID(itemId, bookedDates);
                        FirebaseHelper.incrementValueInParkings(itemId, "mBookedNumber",1);
                        FirebaseHelper.incrementStatistics("Parkings",mHome.getmCountry(),1);
                    }
                }
            });

            mDenyBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(bookID);
                }
            });
        }
    }

    private void showDialog(final String bookID)
    {
        new AlertDialog.Builder(mContext)
                .setTitle("WARNING!")
                .setMessage("Are you sure you want to reject the booking?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("mStatus", ConstantValues.BOOKING_DENIED);
                        mBookingDatabase.child(bookID).updateChildren(map);
                    }
                }).setNegativeButton(R.string.no, null).show();
    }

    @Override
    public int getItemCount() {
        return mBookingManager.size();
    }

}