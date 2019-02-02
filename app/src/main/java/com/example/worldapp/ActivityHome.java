package com.example.worldapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActivityHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        //fragment chooser
        navigation.setOnNavigationItemSelectedListener(this);
        LoadFragment(new FragmentHome());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    private boolean LoadFragment (Fragment fragment)
    {
        if (fragment !=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId())
        {
            case R.id.navigation_home:
                fragment = new FragmentHome();
                break;

            case R.id.navigation_dashboard:
                fragment = new FragmentTrips();
                break;

            case R.id.navigation_notifications:
                fragment = new FragmentInbox();
                break;

            case R.id.navigation_profile:
                fragment = new FragmentProfile();
                break;
        }
        return LoadFragment(fragment);
    }
}
