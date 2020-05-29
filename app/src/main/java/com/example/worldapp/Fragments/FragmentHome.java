package com.example.worldapp.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.OfflineSyncModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        OfflineSyncModel model = new OfflineSyncModel("01032020", "This data is for testing");
        writeToFile(model);
//        doOfflineSync();
        return rootView;
    }

    private void doOfflineSync()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OfflineSyncModel model = new OfflineSyncModel("04032020", "This data is for testing");
                readFromFile();
            }
        }, 10000);
    }

    private void readFromFile() {
        String stringFromFile = "";
        try {
            InputStream inputStream = getActivity().openFileInput("offlineSync.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                stringFromFile = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("declaration activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("declaration activity", "Can not read file: " + e.toString());
        }
        if (stringFromFile == null || stringFromFile.isEmpty())
        {
            return;
        }
        Gson gson = new Gson();
        writeToFirebase(gson.fromJson(stringFromFile, OfflineSyncModel.class));
    }

    public void writeToFile(OfflineSyncModel offlineModel) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput("offlineSync.txt", Context.MODE_PRIVATE));
            Gson gson = new Gson();
            outputStreamWriter.write(gson.toJson(offlineModel));
            outputStreamWriter.close();
            Toast.makeText(getActivity(), "Offline mode : saved in file", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void cleanFile( ) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput("offlineSync.txt", Context.MODE_PRIVATE));
            Gson gson = new Gson();
            outputStreamWriter.write("");
            outputStreamWriter.close();
            Toast.makeText(getActivity(), "Sync completed, local file emptied", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void writeToFirebase(final OfflineSyncModel offlineModel)
    {
        if (!isNetworkConnected())
        {
            writeToFile(offlineModel);
            return;
        }

        FirebaseHelper.mOfflineTestingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OfflineSyncModel offlineSyncModel = dataSnapshot.getValue(OfflineSyncModel.class);
                if (offlineSyncModel.getDate().compareTo(offlineModel.getDate()) > 0)
                {
                    FirebaseHelper.mOfflineTestingDatabase.setValue(offlineModel);
                    Toast.makeText(getActivity(), "Synced the Firebase database", Toast.LENGTH_SHORT).show();
                    cleanFile();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
