package com.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PendingFrag extends Fragment implements recyclerContact.OnMoreInfoClickListener {

    recyclerContact adapter;
    ArrayList<MarkerDetails> list;
    TextView editText,editText1,editText3 , editText4,desedit,dateEditText;
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
        DatabaseReference reference = database.getReference().child("Google markers");


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
//       editText = dialog.findViewById(R.id.editTextText);
//        editText1 = dialog.findViewById(R.id.editTextText4);
//        textView = dialog.findViewById(R.id.textView2);
//        dateEditText = dialog.findViewById(R.id.editTextText2);
//        editText3 = dialog.findViewById(R.id.editTextText8);
//        editText4 = dialog.findViewById(R.id.editTextText9);
        desedit = dialog.findViewById(R.id.editTextDescription);
        desedit.setText(item.getDescription());

        builder.setPositiveButton("Approve", null);

// Set negative button click listener
        builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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


                        // Validate inputs
                        alertDialog.dismiss();
                    }
                });
            }
        });

// Show the dialog
        alertDialog.show();


    }
}