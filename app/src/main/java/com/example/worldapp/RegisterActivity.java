package com.example.worldapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText UsernameET, PasswordET, NameET, FirstnameET;
    FirebaseAuth mAuth;
    Button RegisterButton;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegisterButton = findViewById(R.id.register_button);
        UsernameET = findViewById(R.id.username_edit_text);
        PasswordET = findViewById(R.id.password_edit_text);
        NameET = findViewById(R.id.name_edit_text);
        FirstnameET = findViewById(R.id.firstname_edit_text);

        mAuth = FirebaseAuth.getInstance();


        Intent myIntent = getIntent();
        String[] RecievedArray = myIntent.getStringArrayExtra("extra_array");
        if (RecievedArray[0].isEmpty() && RecievedArray[1].isEmpty()) {
            UsernameET.setText("");
            PasswordET.setText("");
        }
        UsernameET.setText(RecievedArray[0]);
        PasswordET.setText(RecievedArray[1]);



    }

    public void Register(final String Email, final String Password, final String Firstname, final String Name) {
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String, String> UserMap = new HashMap<>();
                            UserMap.put("Name", Name);
                            UserMap.put("Firstname", Firstname);
                            UserMap.put("Email", Email);
                            UserMap.put("Id", "2");

                            mDatabase.setValue(UserMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(myIntent);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void RegisterUser(View view)
    {
        Register(UsernameET.getText().toString(),PasswordET.getText().toString(), FirstnameET.getText().toString(), NameET.getText().toString());
    }

}
