package com.example.worldapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegister extends AppCompatActivity {

    EditText UsernameET, PasswordET, NameET, FirstnameET, PasswordRepeatET;
    FirebaseAuth mAuth;
    Button RegisterButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterButton = findViewById(R.id.register_button);
        UsernameET = findViewById(R.id.username_edit_text);
        PasswordET = findViewById(R.id.password_edit_text);
        PasswordRepeatET = findViewById(R.id.repeat_password_edit_text);
        NameET = findViewById(R.id.name_edit_text);
        FirstnameET = findViewById(R.id.firstname_edit_text);
        progressBar = findViewById(R.id.progress_Bar1);
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
}
