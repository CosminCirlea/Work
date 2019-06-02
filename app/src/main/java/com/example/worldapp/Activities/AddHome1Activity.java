package com.example.worldapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AddHome1Activity extends BaseAppCompat {

    private static final int RESULT_LOAD_IMAGE=1;
    private Button mUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_home1);
        super.SetToolbarTitle("Add photos");
        InitializeView();

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(mIntent,"Select photos"),RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== RESULT_LOAD_IMAGE && resultCode == RESULT_OK)
        {
            if (data.getClipData() != null)
            {

            }
        }
    }

    private void InitializeView()
    {
        mUpload = findViewById(R.id.btn_accommodation_select);
    }

    public void GoToAddHome4(View view) {
        startActivity(new Intent(this, AddHome4Activity.class));
    }
}
