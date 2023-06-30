package com.example.jensfirstapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.jensfirstapp.databinding.FragmentFirstBinding;

import java.util.Map;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setImageView("sample");

        binding.randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImageToCloudinary();
            }
        });
    }

    private void uploadImageToCloudinary() {
        Uri fileUri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/drawable/butterfly");
        MediaManager.get().upload(fileUri).unsigned("unsigned-image").callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String publicId = resultData.get("public_id").toString();
                setImageView(publicId);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
        Toast.makeText(getActivity(),"This worked!!!",Toast.LENGTH_LONG).show();
    }

    private void setImageView(String publicId) {
        String URL = MediaManager.get().url().transformation(new Transformation().effect("cartoonify")).generate(publicId);
        ImageView test = binding.imageview2;
        Glide.with(this).load(URL).into(test);
//        String URL2 = MediaManager.get().url().transformation(new Transformation().effect("grayscale")).generate(publicId2);
//        ImageView practice = binding.imageview2;
//        Glide.with(this).load(URL2).into(practice);
        // create two different functions that each takes a public ID AND an image view

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}