package com.example.worldapp.Helpers;

import android.support.annotation.NonNull;

import com.example.worldapp.Models.GuidedToursModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GetTour implements Continuation<Void, Task<GuidedToursModel>> {

    private String mTourID;

    public GetTour(String tourID)
    {
        mTourID = tourID;
    }

    @Override
    public Task<GuidedToursModel> then(Task<Void> task) {
        final TaskCompletionSource<GuidedToursModel> tcs = new TaskCompletionSource();

        DatabaseReference db = FirebaseHelper.mToursDatabaseReference;

        db.child(mTourID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(GuidedToursModel.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return tcs.getTask();
    }
}