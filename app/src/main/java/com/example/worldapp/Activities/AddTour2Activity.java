package com.example.worldapp.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.worldapp.Models.GuidedToursModel;
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

public class AddTour2Activity extends AppCompatActivity {

    public static final int IMAGE_REQUEST=1;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference, mDatabaseRef;
    private StorageReference mStorageReference;
    private Uri uriProfilePicture;
    private StorageTask uploadTask;
    private ImageView mTourImage;
    private String mTourId, mUserId;
    private TextInputEditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent myIntent = getIntent();
        mTourId = myIntent.getStringExtra("tourId");
        setContentView(R.layout.activity_add_tour2);
        InitializeViews();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserId = mUser.getUid();
        mDatabaseReference = mFirebaseDatabase.getReference("Tours").child(mUser.getUid());
        mStorageReference = FirebaseStorage.getInstance().getReference("TourPictures");

        mTourImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
        mDatabaseRef = mDatabaseReference.child(mTourId);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuidedToursModel tour = dataSnapshot.getValue(GuidedToursModel.class);
                String aux = tour.getmTourImageUrl();
                try {
                    Glide.with(mTourImage.getContext()).load(tour.getmTourImageUrl()).apply(new RequestOptions().placeholder(R.drawable.photo_placeholder).centerCrop()).into(mTourImage);
                }
                catch (Exception e){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openImage() {
        Intent myIntent = new Intent();
        myIntent.setType("image/*");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(myIntent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver mContentResolver = this.getContentResolver();
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
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours").child(mUserId).child(mTourId);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("mTourImageUrl", mUri);
                        mDatabaseReference.updateChildren(map);
                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(AddTour2Activity.this, "Failed upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddTour2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(AddTour2Activity.this, "No image selected", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddTour2Activity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadImage();
            }
        }
    }

    public void AddNewTourPart2(String description)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("TourDescription", description);
        mDatabaseReference.updateChildren(map);
    }

    public void InitializeViews()
    {
        mDescription = findViewById(R.id.et_tour_description);
        mTourImage = findViewById(R.id.iv_add_tour_photo);
    }

    public void GoToAddTour3(View view) {
        Intent myIntent = new Intent(this, AddTour3Activity.class);
        myIntent.putExtra("tourId", mTourId);
        //GetValues();
        AddNewTourPart2(mDescription.getText().toString());
        startActivity(myIntent);
    }
}
