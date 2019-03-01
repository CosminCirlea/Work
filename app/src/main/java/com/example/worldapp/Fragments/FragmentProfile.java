package com.example.worldapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.worldapp.R;

public class FragmentProfile extends Fragment {
    TextView YourProfileDisplayTv;
    Button BtnLogInWithEmail, LogInWithFacebook;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        InitializeViews(view);
        return view;
    }


    public void InitializeViews(View view) {
        YourProfileDisplayTv = view.findViewById(R.id.tv_title);
        BtnLogInWithEmail = view.findViewById(R.id.btn_login_with_email);
    }
}
