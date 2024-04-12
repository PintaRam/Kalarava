package com.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PendingFrag extends Fragment {
    FirebaseAuth firebaseAuth;
    recyclerContact adapter;
    List<contact> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public PendingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference to your data
        DatabaseReference myRef = database.getReference().child("Google markers");//.child(firebaseAuth.getUid());
        String refID ="kara";
// rettrive the data from database
        // Read data
        myRef.child(refID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated.
                MarkerDetails model = dataSnapshot.getValue(MarkerDetails.class);
                double latitude = model.getLatitude();
                double Longitude = model.getLongitude();
                String eventType = model.getEventType();
                String eventName = model.getevent();
                String startDate =model.getStartDate();
                String endDate = model.getEndDate();
                String startTime =model.getStartTime();
                String endTime =model.getEndTime();
                String description =  model.getDescription();
                Log.d("ram",endDate);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //System.out.println("Failed to read value." + error.toException());
                Log.d("event","Event Name");
            }
        });

        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

// Set the adapter

        recyclerView.setAdapter(new recyclerContact(initData()));
        // Inflate the layout for this fragment
        return view;
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_pending, container, false);
    }



    private List<contact> initData() {
        list = new ArrayList<>();
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        list.add(new contact("Conference", "Conference Name", "Location 2"));
        return list;

    }
}