package com.example.worldapp.Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.List;

public class UploadPicturesAdapter extends RecyclerView.Adapter<UploadPicturesAdapter.ViewHolder> {

    public List<String> fileNameList;
    public List<Uri> uriList;

    public UploadPicturesAdapter(List<String> filenameList, ArrayList<Uri> mUriList)
    {
        fileNameList= filenameList;
        uriList = mUriList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.template_upload_photos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String fileName = fileNameList.get(i);
        viewHolder.mFilename.setText(fileName);
        Glide.with(viewHolder.mImage.getContext()).load(uriList.get(i)).apply(new RequestOptions().centerCrop()).into(viewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView mFilename;
        public ImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            mFilename = itemView.findViewById(R.id.tv_row_home_filename);
            mImage = itemView.findViewById(R.id.iv_row_home);
        }
    }
}
