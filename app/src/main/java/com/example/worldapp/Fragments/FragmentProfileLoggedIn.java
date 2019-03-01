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
import com.example.worldapp.Activities.ActivityHome;
import com.example.worldapp.Activities.ActivityLogin;
import com.example.worldapp.Activities.ListingsActivity;
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
    public  Button BtnSignOut, BtnEditProfile, BtnDeleteAccount, BtnMyListings;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private String userID;
    private TextView TvFirstName, TvName;
    public ImageView ivProfilePicture;
    private String auxName, auxFirstname, auxImage;
    StorageReference mStorageReference;
    private Uri uriProfilePicture;
    private StorageTask uploadTask;
    Uri photoUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        InitializeViews(view);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        //String profilePictureUri = getArguments().getString("profilePictureUri");

        setupFirebaseListener();
        BtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d(TAG, "onClick: attempting to sign out the user.");
                FirebaseAuth.getInstance().signOut();
            }
        });

        BtnMyListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListingsActivity.class));
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        if (mUser!=null) {
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showProfileData(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
        mStorageReference = FirebaseStorage.getInstance().getReference("ProfilePictures");
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
                        Uri aux= mUser.getPhotoUrl();
                        Glide.with(getContext()).load(photoUri).into(ivProfilePicture);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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

    private void showProfileData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserDetailsModel uInfo = new UserDetailsModel();
            try{
            uInfo.setName(ds.child(userID).getValue(UserDetailsModel.class).getName());
            uInfo.setImageUri(ds.child(userID).getValue(UserDetailsModel.class).getImageUri());
            uInfo.setFirstname(ds.child(userID).getValue(UserDetailsModel.class).getFirstname());
            TvName.setText(uInfo.getName());
            TvFirstName.setText(uInfo.getFirstname());
            photoUri = Uri.parse(uInfo.getImageUri());
            } catch (Exception e)
            {
                e.getMessage();
            }
        }
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

    //user for image upload
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

    private void InitializeViews(View view)
    {
        BtnSignOut = view.findViewById(R.id.btn_sign_out);
        BtnMyListings = view.findViewById(R.id.btn_my_listings);
        BtnEditProfile = view.findViewById(R.id.btn_edit_profile);
        ivProfilePicture = view.findViewById(R.id.profile_picture);
        TvFirstName = view.findViewById(R.id.tv_profile_first_name);
        TvName = view.findViewById(R.id.tv_profile_family_name);
    }
}