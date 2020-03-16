package com.example.newsmartparkingsystem.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsmartparkingsystem.Model.HistorySlotsModel;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.example.newsmartparkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class HistorySlotsAdapter extends RecyclerView.Adapter<HistorySlotsAdapter.ViewHolder>{

    Context context;
    List<HistorySlotsModel> historySlotsModelList;

    public HistorySlotsAdapter(Context context, List<HistorySlotsModel> historySlotsModelList) {
        this.context = context;
        this.historySlotsModelList = historySlotsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history_slots_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        final HistorySlotsModel historySlotsModel = historySlotsModelList.get(position);

        holder.txtParkingNumber.setText(historySlotsModel.getpNumber());

        holder.txtStatus.setText(historySlotsModel.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass the 'context' here
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Do you want to cancel your Booking?");
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeParkingSlot(historySlotsModel);
                        deleteFromHistory(historySlotsModel,position);

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

    }

    private void removeParkingSlot(final HistorySlotsModel historySlotsModel) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parking Slots");

        reference.child(historySlotsModel.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ParkingSlotsModel parkingSlotsModel = snapshot.getValue(ParkingSlotsModel.class);

                    if (parkingSlotsModel.getpNumber().equals(historySlotsModel.getpNumber())){

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("status","available");

                        snapshot.getRef().updateChildren(hashMap);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void deleteFromHistory(final HistorySlotsModel historySlotsModel1, final int position) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Booking History");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    HistorySlotsModel historySlotsModel = snapshot.getValue(HistorySlotsModel.class);

                    if (historySlotsModel.getTitle().equals(historySlotsModel1.getTitle())){

                        if (historySlotsModel.getpNumber().equals(historySlotsModel1.getpNumber())){

                            snapshot.getRef().removeValue();

                            historySlotsModelList.remove(position);

                            notifyDataSetChanged();

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount(){
        return historySlotsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtParkingNumber,txtStatus;
        CardView cardView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtParkingNumber = itemView.findViewById(R.id.txtParkingNumber);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
