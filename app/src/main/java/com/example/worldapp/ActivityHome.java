package com.example.worldapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
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
                if (mUser != null) {
                    fragment = new FragmentProfileLoggedIn();
                } else {
                    fragment = new FragmentProfile();
                }
                break;
        }
        return LoadFragment(fragment);
    }

    public void SendDataToHomeFragment()
    {

    }

    public void LogInWithEmail(View view) {
        Intent MyIntent = new Intent(this, ActivityLogin.class);
        startActivity(MyIntent);
    }

    public void DeleteAccount(View view) {
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

    public void SignOut(View view) {
    }
}
