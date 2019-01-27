package com.example.worldapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void RegisterUser(View view) {
        EditText UsernameText, PasswordText;
        UsernameText = findViewById(R.id.username_edit_text);
        PasswordText = findViewById(R.id.password_edit_text);
        String[] EmtpyArray =
                {
                        "",""
                };
        String[] ToSendArray=
                {
                        UsernameText.getText().toString(),
                        PasswordText.getText().toString()
                };
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        if (UsernameText.getText()!=null &&  PasswordText.getText()!=null)
            intent.putExtra("extra_array", ToSendArray);
        else
            intent.putExtra("extra_array", "");
        startActivity(intent);
    }
}
