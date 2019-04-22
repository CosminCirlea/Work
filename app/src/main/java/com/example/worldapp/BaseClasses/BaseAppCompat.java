package com.example.worldapp.BaseClasses;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.worldapp.CustomViews.ProgressDialog;
import com.example.worldapp.Interfaces.OnBusyChangedListener;
import com.example.worldapp.R;

public class BaseAppCompat extends AppCompatActivity {

    private ProgressDialog mProgressView;
    private boolean IsBusy;
    protected OnBusyChangedListener mOnBusyChangedListener;

    public ImageView BackButton;
    public RelativeLayout Toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        initialiseProgressDialog();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialiseBackButton();
        Toolbar = findViewById(R.id.toolbar_with_back_button);
        if (Toolbar != null) {
            Toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarColor));
        }
    }

    public boolean IsBusy() {
        return IsBusy;
    }

    public void SetIsBusy(boolean busy) {
        SetIsBusy(busy, true);
    }

    public void SetIsBusy(boolean busy, boolean showProgressDialog) {
        IsBusy = busy;
        if (showProgressDialog) {
            mProgressView.showProgress(busy);
        }
        if (mOnBusyChangedListener != null) {
            mOnBusyChangedListener.onChanged(busy);
        }
    }

    public void SetToolbarTitle(String title) {
        TextView toolbarTitle = findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(title);
    }

    private void initialiseBackButton() {
        BackButton = findViewById(R.id.iv_toolbar_back);
        if (BackButton != null) {
            BackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    private void initialiseProgressDialog() {
        RelativeLayout container = findViewById(R.id.view_container);
        if (container == null) {
            return;
        }
        mProgressView = new ProgressDialog(this, container);
    }
}
