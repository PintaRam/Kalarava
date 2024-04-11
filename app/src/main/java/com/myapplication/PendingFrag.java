package com.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;


public class PendingFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public PendingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments()!=null)
        {  Double latitude = getArguments().getDouble("latitude");
                Double Longitude = getArguments().getDouble("longitude");
            String eventType =  getArguments().getString("eventType");
            String eventName =  getArguments().getString("eventName");
            String startDate =  getArguments().getString("startDate");
            String endDate =  getArguments().getString("endDate");
            String startTime =  getArguments().getString("startTime");
            String endTime =  getArguments().getString("endTime");
            String description =  getArguments().getString("Decription");



        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }
}