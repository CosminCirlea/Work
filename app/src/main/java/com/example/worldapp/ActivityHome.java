package com.example.worldapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.Models.UserDetailsModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class ActivityHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final int IMAGE_REQUEST=1;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;
    private Uri uriProfilePicture;
    ImageView ivProfilePicture;
    private StorageTask uploadTask;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID, userName, userFirstName;
    public FragmentProfileLoggedIn fragmentProfileLoggedIn;
    public FragmentInbox fragmentInbox;
    public FragmentTrips fragmentTrips;
    public FragmentProfile fragmentProfile;
    public FragmentHome fragmentHome;
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

        fragmentHome = new FragmentHome();
        fragmentProfile = new FragmentProfile();
        fragmentTrips = new FragmentTrips();
        fragmentInbox = new FragmentInbox();
        fragmentProfileLoggedIn= new FragmentProfileLoggedIn();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID=mUser.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        //mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());

        /*mStorageReference = FirebaseStorage.getInstance().getReference("ProfilePictures");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetailsModel user = dataSnapshot.getValue(UserDetailsModel.class);
                {
                    if (user.getImageUri().equals("")) {
                        ivProfilePicture.setImageResource(R.mipmap.ic_logo);
                    }
                    else
                    {
                        Glide.with(ActivityHome.this).load(mUser.getPhotoUrl()).into(ivProfilePicture);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUser!=null) {
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try {
                        setProfileDetails(dataSnapshot);
                    }
                    catch (Exception e)
                    {
                        e.getMessage();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver mContentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(uri));
    }

    private void uploadImage()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (uriProfilePicture != null)
        {
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(uriProfilePicture));
            uploadTask = fileReference.getFile(uriProfilePicture);
            uploadTask.continueWith(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageUri", mUri);

                        Bundle bundle = new Bundle();
                        bundle.putString("profilePictureUri", mUri);
                        fragmentProfile.setArguments(bundle);
                        mDatabaseReference.updateChildren(map);
                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(ActivityHome.this, "Failed upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivityHome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(ActivityHome.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            uriProfilePicture = data.getData();
            if (uploadTask != null && uploadTask.isInProgress())
            {
                Toast.makeText(ActivityHome.this,"Upload in progress",Toast.LENGTH_SHORT);
            }
            else
            {
                uploadImage();
            }
        }
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

    private void setProfileDetails(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserDetailsModel uInfo = new UserDetailsModel();
            uInfo.setName(ds.child(userID).getValue(UserDetailsModel.class).getName());
            uInfo.setEmail(ds.child(userID).getValue(UserDetailsModel.class).getEmail());
            uInfo.setFirstname(ds.child(userID).getValue(UserDetailsModel.class).getFirstname());

            userName = uInfo.getName();
            userFirstName =  uInfo.getFirstname();
            fragmentProfileLoggedIn.setProfileDetails(userFirstName, userName);
        }
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

}
