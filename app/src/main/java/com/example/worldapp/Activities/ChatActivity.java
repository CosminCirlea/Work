package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

public class ChatActivity extends BaseAppCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
    }
}
