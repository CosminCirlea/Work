package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.Duration;

public class EditProfileActivity extends BaseAppCompat {

    private CalendarPickerView mCalendarPicker;
    private List<Date> mSelectedDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_edit_profile);
        InitializeViews();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final CalendarPickerView datePicker = findViewById(R.id.calendar_view);
        Date today = new Date();
        final Date tomorrow = new GregorianCalendar(2019, Calendar.MAY, 11).getTime();

        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);
        datePicker.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                if (date.equals(tomorrow)) {
                    datePicker.setDecorators(new ArrayList<CalendarCellDecorator>());
                    return false;
                }
                else
                    return true;
            }
        });
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                mSelectedDates = datePicker.getSelectedDates();

                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTime(date);
                int days = daysBetween(mSelectedDates.get(0), mSelectedDates.get(mSelectedDates.size()-1));

                String selectedDate = String.valueOf(days);
                Toast.makeText(EditProfileActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void InitializeViews()
    {
        mCalendarPicker = findViewById(R.id.calendar_view);
    }
}
