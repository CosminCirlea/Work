package com.example.worldapp.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.ActivityLogin;
import com.example.worldapp.Activities.EditProfileActivity;
import com.example.worldapp.Activities.MyAccommodationsActivity;
import com.example.worldapp.Activities.MyParkingsActivity;
import com.example.worldapp.Activities.MyToursActivity;
import com.example.worldapp.Activities.PaymentActivity;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
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

import static android.app.Activity.RESULT_OK;

public class FragmentProfileLoggedIn extends Fragment {

    public static final int IMAGE_REQUEST=1;
    private static final String TAG = "AccountFragment";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public  Button BtnSignOut, BtnEditProfile, BtnMyTourListings, BtnParkings, BtnAccommodationsListings;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private String userID;
    private TextView TvFirstName, TvName;
    public ImageView ivProfilePicture;
    private StorageReference mStorageReference;
    private Uri uriProfilePicture;
    private StorageTask uploadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        InitializeViews(view);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = UserCore.Instance().getmFirebaseUser();
        userID = mUser.getUid();
        setupFirebaseListener();
        InitialValues();

        BtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                UserCore.Instance().User = null;
                UserCore.Instance().setLoggedIn(false);
            }
        });

        BtnMyTourListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyToursActivity.class));
            }
        });

        BtnParkings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyParkingsActivity.class));
            }
        });

        BtnAccommodationsListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyAccommodationsActivity.class));
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        mStorageReference = FirebaseStorage.getInstance().getReference("ProfilePictures");
        mDatabaseReference = FirebaseDatabase.getInstance()
                .getReference("users").child(mUser.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetailsModel user = dataSnapshot.getValue(UserDetailsModel.class);
                if (user.getImageUri().equals("")) {
                        ivProfilePicture.setImageResource(R.mipmap.ic_logo);
                    } else {
                        Glide.with(ivProfilePicture.getContext()).load(user.getImageUri())
                                .into(ivProfilePicture);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        BtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });

        return view;
    }

    private void setupFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                    Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    private void openImage() {
        Intent myIntent = new Intent();
        myIntent.setType("image/*");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(myIntent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver mContentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(uri));
    }

    private void uploadImage()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if (uriProfilePicture != null)
        {
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(uriProfilePicture));
            uploadTask = fileReference.putFile(uriProfilePicture);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
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
                        UserCore.Instance().setImageUri(mUri);
                        mDatabaseReference.updateChildren(map);
                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    //used for image upload
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            uriProfilePicture = data.getData();
            if (uploadTask != null && uploadTask.isInProgress())
            {
                Toast.makeText(getContext(),"Upload in progress",Toast.LENGTH_SHORT);
            }
            else
            {
                uploadImage();
            }
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthStateListener != null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }

    private void InitialValues()
    {
        if (UserCore.Instance().User!=null){
        String firstName = UserCore.Instance().User.getFirstname();
        String name = UserCore.Instance().User.getName();
        TvName.setText(name);
        TvFirstName.setText(firstName);
        }
    }

    private void InitializeViews(View view)
    {
        BtnSignOut = view.findViewById(R.id.btn_sign_out);
        BtnMyTourListings = view.findViewById(R.id.btn_my_tour_listings);
        BtnEditProfile = view.findViewById(R.id.btn_edit_profile);
        BtnParkings = view.findViewById(R.id.btn_parking_listings);
        ivProfilePicture = view.findViewById(R.id.profile_picture);
        TvFirstName = view.findViewById(R.id.tv_profile_first_name);
        TvName = view.findViewById(R.id.tv_profile_family_name);
        BtnAccommodationsListings = view.findViewById(R.id.btn_my_accommodations_listings);
    }
}