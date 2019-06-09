package com.example.worldapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.worldapp.Fragments.FragmentHome;
import com.example.worldapp.Fragments.FragmentInbox;
import com.example.worldapp.Fragments.FragmentProfile;
import com.example.worldapp.Fragments.FragmentProfileLoggedIn;
import com.example.worldapp.Fragments.FragmentTrips;
import com.example.worldapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ActivityHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabaseReference;

    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private FragmentProfileLoggedIn fragmentProfileLoggedIn;
    private FragmentInbox fragmentInbox;
    private FragmentTrips fragmentTrips;
    private FragmentProfile fragmentProfile;
    private FragmentHome fragmentHome;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_home);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        InitializeViews();
        LoadFragment(fragmentHome);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null)
            userID = mUser.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = fragmentHome;
                break;

            case R.id.navigation_dashboard:
                fragment = fragmentTrips;
                break;

            case R.id.navigation_notifications:
                fragment = fragmentInbox;
                break;

            case R.id.navigation_profile:
                if (mUser != null) {
                    fragment = fragmentProfileLoggedIn;
                } else {
                    fragment = fragmentProfile;
                }
                break;
        }
        return LoadFragment(fragment);
    }

    private boolean LoadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        } else
            return false;
    }

    public void LogInWithEmail(View view) {
        Intent MyIntent = new Intent(this, ActivityLogin.class);
        startActivity(MyIntent);
    }

    public void DeleteAccount(View view) {
        new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage(R.string.delete_account)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });
                    }
                }).setNegativeButton(R.string.no, null).show();
    }

    private void InitializeViews() {
        fragmentHome = new FragmentHome();
        fragmentProfile = new FragmentProfile();
        fragmentTrips = new FragmentTrips();
        fragmentInbox = new FragmentInbox();
        fragmentProfileLoggedIn = new FragmentProfileLoggedIn();
    }

    public void ShowTours(View view) {
        Intent mIntent = new Intent(ActivityHome.this, ListAllToursActivity.class);
        startActivity(mIntent);
    }

    public void ShowParkings(View view) {
        Intent mIntent = new Intent(ActivityHome.this, ListParkingsActivity.class);
        startActivity(mIntent);
    }

    public void ShowAccommodation(View view) {
        Intent mIntent = new Intent(ActivityHome.this, ListAllAccommodationActivity.class);
        startActivity(mIntent);
    }

    public void OnAcceptBooking(View view) {

    }

    public void OnDenyBooking(View view) {
    }


}

