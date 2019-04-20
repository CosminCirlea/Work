package com.example.worldapp.BaseClasses;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BaseAppCompat extends AppCompatActivity {

    private ProgressDialog mProgressView;
    private RelativeLayout mProgressViewContainer;
    private boolean IsBusy;
   // protected OnBusyChangedListener mOnBusyChangedListener;

    public ImageView BackButton;
    public RelativeLayout Toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
    }
}
