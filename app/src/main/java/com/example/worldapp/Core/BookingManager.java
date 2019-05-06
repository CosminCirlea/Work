package com.example.worldapp.Core;

import java.util.Date;
import java.util.List;

public class BookingManager {
    private static BookingManager mBookingManager;
    private String mOwnerID;
    private String mBookingID;
    private List<Date> mSelectedDates;
    private double mItemsPrice;
    private double mAppFee;
    private double mTotalCost;

    public static BookingManager Instance()
    {
        if (mBookingManager == null)
        {
            mBookingManager = new BookingManager();
        }
        return mBookingManager;
    }


}
