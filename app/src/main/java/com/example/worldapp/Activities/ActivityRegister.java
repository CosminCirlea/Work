package com.example.worldapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegister extends AppCompatActivity {

    EditText UsernameET, PasswordET, NameET, FirstnameET, PasswordRepeatET, PhoneNumberET, AddressLineET;
    FirebaseAuth mAuth;
    Button RegisterButton;
    ProgressBar progressBar;
    private FirebaseUser mUser;
    private String mUserId;
    private DatabaseReference mDatabaseReference;
//TODO To be able to resend email verification for registration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_register);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        InitializeViews();
        SetWhitelabelColors();
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);
        try {
            Intent myIntent = getIntent();
            String[] RecievedArray = myIntent.getStringArrayExtra("extra_array");
            if (RecievedArray[0].isEmpty() && RecievedArray[1].isEmpty()) {
                UsernameET.setText("");
                PasswordET.setText("");
            }
            UsernameET.setText(RecievedArray[0]);
            PasswordET.setText(RecievedArray[1]);
        } catch (NullPointerException e) {
        }
    }

    public void Register(final String Email, final String Password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            if (CheckPasswordsMatch(Password, PasswordRepeatET.getText().toString())) {
                                // Sign in success, update UI with the signed-in user's information
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ActivityRegister.this, "Registered succesfully!" +
                                                    "\nPlease confirm email!", Toast.LENGTH_LONG).show();
                                            mUser = mAuth.getCurrentUser();
                                            mUserId = mUser.getUid();
                                            writeNewUser(mUserId, FirstnameET.getText().toString(),NameET.getText().toString(), UsernameET.getText().toString(), PhoneNumberET.getText().toString());
                                            Intent myIntent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                            startActivity(myIntent);
                                        } else {
                                            Toast.makeText(ActivityRegister.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivityRegister.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void RegisterUser(View view) {
        Register(UsernameET.getText().toString(), PasswordET.getText().toString());
    }

    public boolean CheckPasswordsMatch(String password, String passwordRepeat) {
        if (password.equals(passwordRepeat))
            return true;
        else {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT);
            return false;
        }
    }

    private void writeNewUser(String userId, String firstName, String name, String email, String phoneNumber) {
        UserDetailsModel user = new UserDetailsModel(userId, firstName, name, email, Double.parseDouble("0"), "",phoneNumber);

        mDatabaseReference.child("users").child(userId).setValue(user);
    }

    private void InitializeViews()
    {
        RegisterButton = findViewById(R.id.register_button);
        UsernameET = findViewById(R.id.username_edit_text);
        PasswordET = findViewById(R.id.password_edit_text);
        PasswordRepeatET = findViewById(R.id.repeat_password_edit_text);
        NameET = findViewById(R.id.name_edit_text);
        FirstnameET = findViewById(R.id.firstname_edit_text);
        PhoneNumberET = findViewById(R.id.phone_number_edit_text);
        AddressLineET= findViewById(R.id.address_line_registration);
        progressBar = findViewById(R.id.progress_Bar1);
    }

    private void SetWhitelabelColors()
    {
        int hintColor = Color.parseColor("#A0A0A0");
        UsernameET.setHintTextColor(hintColor);
        PasswordET.setHintTextColor(hintColor);
        PasswordRepeatET.setHintTextColor(hintColor);
        FirstnameET.setHintTextColor(hintColor);
        NameET.setHintTextColor(hintColor);
        PhoneNumberET.setHintTextColor(hintColor);
        AddressLineET.setHintTextColor(hintColor);
    }
}
