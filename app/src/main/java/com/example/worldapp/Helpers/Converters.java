package com.example.worldapp.Helpers;

import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Converters {
    public static Converters mConverter;

    public static Converters Instance()
    {
        if(mConverter == null)
        {
            mConverter = new Converters();
        }
        return mConverter;
    }

    public static String DateToString(Date startDate) {
        startDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr = format.format(startDate);
        return DateToStr;
    }
}
