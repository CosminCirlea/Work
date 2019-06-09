package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLogin extends BaseAppCompat {

    private FirebaseAuth mAuth;
    public EditText UsernameET, PasswordET;
    private Button mLogInButton;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private String mTourID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        InitializeViews();
        super.SetToolbarTitle("Log In");
        try {
            Intent myIntent = new Intent();
            mTourID = myIntent.getStringExtra("tourID");
        }
        catch (Exception e)
        {}
        mAuth = FirebaseAuth.getInstance();
        UsernameET.setText("cirlea@cuvox.de");
        PasswordET.setText("cosmin");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void RegisterUser(View view) {
        EditText UsernameText, PasswordText;
        UsernameText = findViewById(R.id.username_edit_text);
        PasswordText = findViewById(R.id.password_edit_text);
        String[] ToSendArray=
                {
                        UsernameText.getText().toString(),
                        PasswordText.getText().toString()
                };
        Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
        if (UsernameText.getText()!=null &&  PasswordText.getText()!=null)
            intent.putExtra("extra_array", ToSendArray);
        else
            intent.putExtra("extra_array", "");
        startActivity(intent);
        PasswordET.setText("cosmin");
        UsernameET.setText("cosmin@gustr.com");
    }

    public void LogInClick(View view) {
        String email = UsernameET.getText().toString();
        String password = PasswordET.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified())
                            {
                                if (mTourID == null || mTourID.isEmpty()) {
                                    Intent intent = new Intent(ActivityLogin.this, SplashActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                }
                            }
                            else {
                                Toast.makeText(ActivityLogin.this, "Please verify your email address!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLogin.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onSignInSuccessfull(final FirebaseUser user) {
        UserCore.Instance().setmFirebaseUser(user);
        UserCore.Instance().User.setUserId(user.getUid());

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("users");
        DatabaseReference userData = mReference.child(user.getUid());
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!UserCore.Instance().isLoggedIn())
                {
                    UserDetailsModel userData = dataSnapshot.getValue(UserDetailsModel.class);
                    if(userData.getUserId().contains(user.getUid()))
                    {
                        UserCore.Instance().setLoggedIn(true);
                        UserCore.Instance().setmUser(userData);

                        //SetIsBusy(false);

                        Intent intent = new Intent(ActivityLogin.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    private void OpenTourByID()
    {

    }

    private void InitializeViews()
    {
        UsernameET = findViewById(R.id.username_edit_text);
        PasswordET = findViewById(R.id.password_edit_text);
        mLogInButton = findViewById(R.id.login_button);
    }
}
