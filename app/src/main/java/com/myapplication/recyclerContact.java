package com.myapplication;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerContact extends RecyclerView.Adapter<recyclerContact.viewholder> {
    // Context context;
    ArrayList<MarkerDetails>list;
    private OnMoreInfoClickListener listener;

    recyclerContact(Context context, ArrayList<MarkerDetails>list,OnMoreInfoClickListener listener)
    {
        //this.context = context;
        this.list = list;
        this.listener = listener;

    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contact,parent,false);
        viewholder viewholder = new viewholder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txt1.setText(list.get(position).getEventType());
        holder.txt2.setText(list.get(position).getEventType());
        holder.txt3.setText("ram");
       holder.moreInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.onMoreInfoClicked(holder.getAdapterPosition());

           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txt1, txt2,txt3;
        Button moreInfo;

        RelativeLayout layout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            moreInfo = itemView.findViewById(R.id.moreinfo);

            layout = itemView.findViewById(R.id.ll1);
        }
    }
    public interface OnMoreInfoClickListener {
        void onMoreInfoClicked(int position);
    }




}
