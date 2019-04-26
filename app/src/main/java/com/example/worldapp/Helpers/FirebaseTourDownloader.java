package com.example.worldapp.Helpers;

import android.os.AsyncTask;

import com.example.worldapp.Models.GuidedToursModel;

import java.net.URL;
import java.util.ArrayList;

public class FirebaseTourDownloader extends AsyncTask<URL, ArrayList<GuidedToursModel>, Long> {
    @Override
    protected Long doInBackground(URL... urls) {
        return null;
    }
}
