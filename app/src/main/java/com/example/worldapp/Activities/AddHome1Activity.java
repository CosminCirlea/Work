package com.example.worldapp.Activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.worldapp.Adapters.UploadPicturesAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.example.worldapp.Helpers.FirebaseHelper.mAccommodationDatabaseReference;

public class AddHome1Activity extends BaseAppCompat {

    private static final int RESULT_LOAD_IMAGE=1;
    private Button mUpload , mFinishButton;
    private RecyclerView mUploadList;
    private ArrayList<Uri> mImagesURIs;
    private StorageTask uploadTask;
    private ArrayList<String> mImagesStrings= new ArrayList<>();
    private UploadPicturesAdapter mPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_home1);
        super.SetToolbarTitle("Add photos");
        InitializeView();

        mImagesURIs = new ArrayList<>();

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(mIntent,"Select photos"),RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getClipData() != null)
        {
            mImagesURIs.clear();
            ClipData clipData = data.getClipData();
            for(int i = 0; i < clipData.getItemCount(); i++){
                Uri mUri =  clipData.getItemAt(i).getUri();
                mImagesURIs.add(mUri);
            }

            if (uploadTask != null && uploadTask.isInProgress())
            {
                Toast.makeText(AddHome1Activity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadImage();
            }
        }
    }

    private void uploadImage()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        final ArrayList<String> imageList = new ArrayList<>();
        if (mImagesURIs != null)
        {
            for (Uri homeUri : mImagesURIs){
            final StorageReference fileReference = FirebaseHelper.mAccommodationStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(homeUri));
            uploadTask = fileReference.putFile(homeUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    mImagesStrings.add(fileReference.getName());
                    mPictureAdapter.notifyDataSetChanged();
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        imageList.add(mUri);
                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(AddHome1Activity.this, "Failed upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddHome1Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
            }
        }
        AccommodationCore.Instance().setmImagesUrls(imageList);
        SetAdapter();
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver mContentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(uri));
    }

    private void SetAdapter()
    {
        if (mImagesStrings != null){
            mPictureAdapter = new UploadPicturesAdapter(mImagesStrings, mImagesURIs);
        }

        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(mPictureAdapter);
    }

    private void InitializeView()
    {
        mUpload = findViewById(R.id.btn_upload_photos);
        mUploadList = findViewById(R.id.rv_upload_list);
        mFinishButton = findViewById(R.id.btn_accommodation_select);
    }

    public void FinishRegistration(View view) {
        mAccommodationDatabaseReference.child(AccommodationCore.Instance().getHomeId()).setValue(AccommodationCore.Instance());
        startActivity(new Intent(this , ActivityHome.class));
        Toast.makeText(this, "Accommodation added succesfully!", Toast.LENGTH_LONG).show();
    }
}
