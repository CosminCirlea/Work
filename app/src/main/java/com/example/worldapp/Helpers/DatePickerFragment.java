package com.example.worldapp.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.worldapp.Constants.ConstantValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.worldapp.Activities.ToursFilterActivity.mChosenDate;

public final class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0);
        Date date = calendar.getTime();
        SimpleDateFormat formatTime = new SimpleDateFormat(ConstantValues.DATE_FORMAT);
        mChosenDate = formatTime.format(date);
        //mTourDate.setText(mChosenDate);
    }
}