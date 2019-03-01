package com.example.worldapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MyHomesListingsAdapter extends
    RecyclerView.Adapter<MyHomesListingsAdapter.ViewHolder> {

    private TextView tvAnnouncementTitle, tvLocation, tvHouseType, tvMaxGuests, tvBeds, tvPricePerNight;
    private List<HomeDetailsModel> mHomes;

    public MyHomesListingsAdapter(List<HomeDetailsModel> homes) {
        mHomes = homes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
        InitializeViews(itemView);
    }
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.template_row_listed_homes, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HomeDetailsModel home = mHomes.get(position);
        String location =home.getCity()+"," + home.getRegion() +","+home.getCountry();
        tvAnnouncementTitle.setText(home.getAnnouncementTitle());
        tvLocation.setText(location);
        tvHouseType.setText(home.getListingType() + " - "+ home.getOwnerType());
        tvBeds.setText(String.valueOf(home.getBedsToUse())+" - " +home.getRoomsToUse());
        tvPricePerNight.setText(String.valueOf(home.getPricePerNight())+Currency.getInstance(Locale.GERMANY).getCurrencyCode()+" per night ");
    }

    @Override
    public int getItemCount() {
        return mHomes.size();
    }

    private void InitializeViews(View itemView)
    {
        tvAnnouncementTitle = itemView.findViewById(R.id.tv_announcement_title);
        tvLocation = itemView.findViewById(R.id.tv_location);
        tvHouseType = itemView.findViewById(R.id.tv_venue_type);
        tvMaxGuests = itemView.findViewById(R.id.tv_guest_capacity);
        tvBeds = itemView.findViewById(R.id.tv_beds);
        tvPricePerNight= itemView.findViewById(R.id.tv_price_per_night);
    }
}
