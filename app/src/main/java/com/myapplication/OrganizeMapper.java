package com.myapplication;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.myapplication.databinding.ActivityOrganizeMapperBinding;

import java.util.Calendar;

public class OrganizeMapper extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    final int PERMISSION_REQUEST_CODE=1001;
    final int REQUEST_CODE=101;
    private ActivityOrganizeMapperBinding binding;
    EditText editText,editText1,editText3 , editText4;
    TextView textView;
    private Calendar calendar;
    EditText dateEditText;
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
    public static boolean isGPSEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Method to show dialog to turn on location services
    private void showLocationTurnDialog() {
        if (!OrganizeMapper.isGPSEnabled(this)) {


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
                    mMap.addMarker(new MarkerOptions().position(myloc).title("My Location").icon(bitdescriber(getApplicationContext(),R.drawable.home)));
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
        // Custom marker icon
        //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marriage);

        // Add a marker at the clicked location and customize it
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .icon(icon)
//                .title("Custom Marker"));
        // You can also customize other properties of the marker, such as title, snippet, etc.
        // mMap.addMarker(new MarkerOptions().position(latLng).icon(icon).title("Custom Marker").snippet("This is a custom marker"));

        // Optionally, you can move the camera to the clicked location
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Marker Type")
                .setItems(new CharSequence[]{"Hospitals","Marriage", "Hackathons", "Institutes","Parking","Banks","Police"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle marker type selection
                        switch (which) {
                            case 0:
                                // Type 1 marker
                                addCustomMarker("Hospital" ,latLng, R.drawable.hospital);
                                break;
                            case 1:
                                // Type 2 marker
                                addCustomMarker("Marriage",latLng, R.drawable.marriage);
                                break;
                            case 2:
                                // Type 3 marker
                                addCustomMarker("HackThon",latLng, R.drawable.hack);
                                break;
                            case 3:
                                addCustomMarker("Education",latLng, R.drawable.edu);
                                break;
                            case 4:
                                addCustomMarker("Parking",latLng, R.drawable.parking);
                                break;
                            case 5:
                                addCustomMarker("Bank",latLng, R.drawable.bank);
                                break;
                            case 6:
                                addCustomMarker("Police",latLng, R.drawable.police);
                                break;
                        }
                    }
                });
        builder.show();
    }

//For setting Custmised icons from drawable
    private void addCustomMarker(String str ,LatLng latLng, int markerDrawableId) {
        // Custom marker icon
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(markerDrawableId);

        // Add a marker at the clicked location and customize it
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .title("Custom Marker"));

        // Optionally, you can move the camera to the clicked location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_dialog);
        View view = getLayoutInflater().inflate(R.layout.cust_dialog, (ViewGroup) findViewById(R.id.rel));
        dialog.setContentView(view);
        dialog.setCancelable(false);// should not close after clicking outside the dialog box
        Button btn = dialog.findViewById(R.id.button);
        editText = dialog.findViewById(R.id.editTextText);
        editText1  = dialog.findViewById(R.id.editTextText4);
        textView = dialog.findViewById(R.id.textView2);
        dateEditText = dialog.findViewById(R.id.editTextText2);
        editText3 = dialog.findViewById(R.id.editTextText8);
        editText4 = dialog.findViewById(R.id.editTextText9);
        textView.setText(str);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dateEditText);
            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editText3);
            }
        });

        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(editText4);
            }
        });

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(editText1);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString()))
                {
                    editText.setError("Please Enter the Name ");
                } else if (TextUtils.isEmpty(editText1.getText().toString())) {
                    editText1.setError("Please select the time");
                } else if (TextUtils.isEmpty(dateEditText.getText().toString())) {
                    dateEditText.setError("Please Select the date");
                }
                else {
                    dialog.dismiss();
                }

            }
        });
        dialog.show();


    }


    private void showTimePickerDialog(EditText editText) {
        // Get current time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update EditText with selected time
                        editText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, false);

        // Show the dialog
        timePickerDialog.show();
    }


    private void showDatePickerDialog(EditText dateEditText) {

        DialogFragment newFragment = new DatePickerFragment(dateEditText);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }




    private BitmapDescriptor bitdescriber(Context ctx,int vectorread)
    {
        Drawable vectordraw=ContextCompat.getDrawable(ctx,vectorread);
        vectordraw.setBounds(0,0,vectordraw.getIntrinsicWidth(),vectordraw.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectordraw.getIntrinsicWidth(),vectordraw.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectordraw.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}