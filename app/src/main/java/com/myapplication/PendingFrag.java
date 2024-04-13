package com.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PendingFrag extends Fragment implements recyclerContact.OnMoreInfoClickListener {
    private GoogleMap mMap;
    recyclerContact adapter;
    ArrayList<MarkerDetails> list;
    TextView eventName,startDate,endDate ,startTime,description,endTime,eventType,location;
    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public PendingFrag() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference to your data
        // DatabaseReference myRef = database.getReference().child("Google markers");//.child(firebaseAuth.getUid());
        //  String refID ="kara";
// rettrive the data from database
        // Read data
        DatabaseReference reference = database.getReference().child("Pending");


        View view = inflater.inflate(R.layout.fragment_pending, container, false);
       list = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new recyclerContact(getActivity(),list,this);
        recyclerView.setAdapter(adapter);

// Set the adapter

//        list.add(new MarkerDetails("Conference", "Conference Name", "Location 2"));
//        list.add(new MarkerDetails("Conference", "Conference Name", "Location 2"));
//        list.add(new MarkerDetails("Conference", "Conference Name", "Location 2"));
//        list.add(new MarkerDetails("Conference", "Conference Name", "Location 2"));
//        list.add(new MarkerDetails("Conference", "Conference Name", "Location 2"));

        //recyclerView.setAdapter(new recyclerContact(getContext(), list));
        // Inflate the layout for this fragment\


        //database work

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated.
                //  MarkerDetails model = dataSnapshot.getValue(MarkerDetails.class);
//////////////////////adding the data from database to list
                for (DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    MarkerDetails eventDetails = Snapshot.getValue(MarkerDetails.class);
                    list.add(eventDetails);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //System.out.println("Failed to read value." + error.toException());
                Log.d("event","Failed to retrive the data");
            }
        });









        return view;
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onMoreInfoClicked(int position) {
        Log.d("ram","showdialog");
        MarkerDetails item = list.get(position);
        showDialog(item);

    }


    //private List<contact> initData() {
//        list = new ArrayList<>();
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
//        list.add(new contact("Conference", "Conference Name", "Location 2"));
  //    return list;

    //}
    public void showDialog(MarkerDetails item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.approvedialog, null);
        builder.setView(dialog);

        builder.setCancelable(false);

        //finding the id of the dialog box
       eventName = dialog.findViewById(R.id.editTextText);
        endTime = dialog.findViewById(R.id.editTextText4);
        eventType = dialog.findViewById(R.id.textView2);
        startDate = dialog.findViewById(R.id.editTextText2);
        endDate = dialog.findViewById(R.id.editTextText8);
        startTime = dialog.findViewById(R.id.editTextText9);
        description = dialog.findViewById(R.id.editTextDescription);
        location =dialog.findViewById(R.id.location);

        //getting latitide and location
        Double latitude = item.getLatitude();
        Double longitude = item.getLongitude();
        String city = "Location Not found";
        //finding place where the event is happening
        Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                city = address.getAddressLine(0);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //retriving the deatils from pending database
        String eventType1 = item.getEventType();
        String eventName1= item.getevent();
        String startDate1 = item.getStartDate();
        String startTime1 = item.getStartTime();
        String endDate1 = item.getEndDate();
        String endTime1 = item.getEndTime();
        String description1 = item.getDescription();


        //seting the text in dialog box

        eventType.setText(item.getEventType());
        eventName.setText(" Event : "+item.getevent());
        startDate.setText(item.getStartDate());
        endDate.setText(item.getEndDate());
        startTime.setText(item.getStartTime());
        endTime.setText(item.getEndTime());
        description.setText(item.getDescription());
        location.setText(city);
        int markerDrawableId = item.getMarkerDrawableId();
        LatLng latLng = new LatLng(latitude, longitude);
        Log.d("ram","ram"+markerDrawableId);




        builder.setPositiveButton("Approve", null);

// Set negative button click listener
        builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            storeMarkerDetailsInFirebaseRejected(markerDrawableId,latLng,eventType1,eventName1,startDate1,startTime1,endDate1,endTime1,description1);





                dialogInterface.cancel();
            }
        });

// Create the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        storeMarkerDetailsInFirebaseApproved(markerDrawableId,latLng,eventType1,eventName1,startDate1,startTime1,endDate1,endTime1,description1);
                        // Validate inputs
                        alertDialog.dismiss();
                    }
                });
            }
        });

// Show the dialog
        alertDialog.show();


    }

    private void storeMarkerDetailsInFirebaseApproved(int markerDrawableId,LatLng latLng,String eventType,String eventName,String startDate,String startTime,String endDate,String endTime,String description) {
        // Store marker details in Firebase Realtime Database
        DatabaseReference markersRef = FirebaseDatabase.getInstance().getReference("Approved");
        String markerId = markersRef.push().getKey();

        if (markerId != null) {
            MarkerDetails markerDetails = new MarkerDetails(markerDrawableId,latLng.latitude, latLng.longitude, eventType, eventName, startDate, startTime, endDate, endTime, description);
            markersRef.child(markerId).setValue(markerDetails);
        }
//        Bundle bundle = new Bundle();
//
//        bundle.putString("eventName",eventName);



    }
    private void storeMarkerDetailsInFirebaseRejected(int markerDrawableId,LatLng latLng,String eventType,String eventName,String startDate,String startTime,String endDate,String endTime,String description) {
        // Store marker details in Firebase Realtime Database
        DatabaseReference markersRef = FirebaseDatabase.getInstance().getReference("Reject");
        String markerId = markersRef.push().getKey();

        if (markerId != null) {
            MarkerDetails markerDetails = new MarkerDetails(markerDrawableId,latLng.latitude, latLng.longitude, eventType, eventName, startDate, startTime, endDate, endTime, description);
            markersRef.child(markerId).setValue(markerDetails);
        }
//        Bundle bundle = new Bundle();
//
//        bundle.putString("eventName",eventName);



    }
}