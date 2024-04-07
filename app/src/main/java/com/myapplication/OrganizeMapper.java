package com.myapplication;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapplication.databinding.ActivityOrganizeMapperBinding;

import java.util.Calendar;
import java.util.Locale;

public class OrganizeMapper extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    final int PERMISSION_REQUEST_CODE=1001;
    final int REQUEST_CODE=101;
    private ActivityOrganizeMapperBinding binding;
    private EditText editTextTime,editTextDate;
    private String eventname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Request for location access


        binding = ActivityOrganizeMapperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(OrganizeMapper.this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        get_Location();
        mMap.setOnMapClickListener(this);
        //LatLng sydney = new LatLng(13.1169, 77.6346);

    }

    // Method to show dialog to turn on location services
    private void showLocationTurnDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Location services are disabled. Do you want to enable them?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open location settings
                        Intent enableLocationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(enableLocationIntent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void get_Location()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

//        // after requesting permissions   check whether GPS is Enabled or not
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            // Location services are not enabled, prompt user to enable it
//
//            Intent enableLocationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(enableLocationIntent);
//        }

        showLocationTurnDialog();
        // Inside onCreate() method, after checking location settings
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Use the location object to get latitude and longitude
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng myloc=new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(myloc).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.home)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc,20));

                    // Do something with the obtained latitude and longitude
                    Toast.makeText(OrganizeMapper.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                } else {
                    // Unable to retrieve location
                    Toast.makeText(OrganizeMapper.this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Location retrieval failed
                Toast.makeText(OrganizeMapper.this, "Location retrieval failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapClick(LatLng latLng) {
      showMarkerDetailsDialog(latLng);
    }
        //For storing marker Info in
        private void showMarkerDetailsDialog ( final LatLng latLng){
            AlertDialog.Builder builder = new AlertDialog.Builder(OrganizeMapper.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.activity_marker_details_dialog, null);
            builder.setView(dialogView);

             editTextTime = dialogView.findViewById(R.id.editTextTime);
             editTextDate = dialogView.findViewById(R.id.editTextDate);
            final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
            final Spinner spinnerEventType = dialogView.findViewById(R.id.spinnerEventType);
            final EditText eventId =dialogView.findViewById(R.id.eventName);



            // Populate the spinner with event types
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.event_types_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEventType.setAdapter(adapter);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                     eventname=eventId.getText().toString();
                    String time = editTextTime.getText().toString();
                    String date = editTextDate.getText().toString();
                    String description = editTextDescription.getText().toString();
                    String eventType = spinnerEventType.getSelectedItem().toString();

                    // Add marker with the provided details
                    addCustomMarker(latLng, eventType, time, date, description);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();
        }

    private void addCustomMarker(LatLng latLng, String eventType, String time, String date, String description) {
        // Get the corresponding marker icon based on event type
        int markerDrawableId = getMarkerDrawableId(eventType);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(markerDrawableId);

        // Add a marker at the clicked location and customize it
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .title("Custom Marker")
                .snippet("Type: " + eventType + ", Time: " + time + ", Date: " + date + ", Description: " + description);
        mMap.addMarker(markerOptions);

        // Optionally, you can move the camera to the clicked location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        // Optionally, store marker details in Firebase Realtime Database
        storeMarkerDetailsInFirebase(latLng, eventType, time, date, description);
    }

    private int getMarkerDrawableId(String eventType) {
        // Implement logic to map event type to corresponding marker drawable resource
        // For example:
        switch (eventType) {
            case "Hospitals":
                return R.drawable.hospital;
            case "Marriage":
                return R.drawable.marriage;
            case "Hackathon":
                return R.drawable.hack;
            case "Bank":
                return  R.drawable.bank;
            case "Educational":
                return R.drawable.edu;
            case "Police":
                return R.drawable.police;
            case "Parking":
                return R.drawable.parking;
            default:
                return R.drawable.home;
        }
    }

    private void storeMarkerDetailsInFirebase(LatLng latLng, String eventType, String time, String date, String description) {
        // Store marker details in Firebase Realtime Database
        DatabaseReference markersRef = FirebaseDatabase.getInstance().getReference("Google markers");
        String markerId = markersRef.push().getKey();

        if (markerId != null) {
            MarkerDetails markerDetails = new MarkerDetails(eventname,latLng.latitude, latLng.longitude, eventType, time, date, description);
            markersRef.child(eventname).setValue(markerDetails);
        }
    }

    //inflatted activities methods
    public void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display the selected date in the EditText

                editTextDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void openTimePicker(View view) {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog and set the listener to handle the selected time
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Display the selected time in the EditText
                        editTextTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, true); // true for 24 hour time

        // Show the TimePickerDialog when EditText is clicked
        timePickerDialog.show();
    }


}