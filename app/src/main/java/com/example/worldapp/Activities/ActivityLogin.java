package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldapp.Common.Common;
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

public class ActivityLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public EditText UsernameET, PasswordET;
    private Button mLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        InitializeViews();
        mAuth = FirebaseAuth.getInstance();

        /* TODO WHEN STORING USERS
        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("users");

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email = UsernameET.getText().toString();
                        String password = PasswordET.getText().toString();
                        UserDetailsModel user = dataSnapshot.child(email).getValue(UserDetailsModel.class);
                        if (user.get)
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
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
                                Intent myIntent = new Intent(ActivityLogin.this, ActivityHome.class);
                                startActivity(myIntent);
                                finish();
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

    private void InitializeViews()
    {
        UsernameET = findViewById(R.id.username_edit_text);
        PasswordET = findViewById(R.id.password_edit_text);
        mLogInButton = findViewById(R.id.login_button);
    }
}
