package com.example.worldapp.Helpers;


public class TourHelper {
    private static TourHelper mTourHelper;


    public static TourHelper Instance()
    {
        if (mTourHelper == null)
        {
            mTourHelper = new TourHelper();
        }
        return mTourHelper;
    }
}
