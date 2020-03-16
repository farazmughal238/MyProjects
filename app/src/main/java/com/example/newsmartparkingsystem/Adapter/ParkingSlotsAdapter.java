package com.example.newsmartparkingsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsmartparkingsystem.BookingActivity;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.example.newsmartparkingsystem.R;

import java.util.List;

public class ParkingSlotsAdapter extends RecyclerView.Adapter<ParkingSlotsAdapter.ViewHolder> {

    Context context;
    List<ParkingSlotsModel> parkingSlotsModelList;

    public ParkingSlotsAdapter(Context context, List<ParkingSlotsModel> parkingSlotsModelList){
        this.context = context;
        this.parkingSlotsModelList = parkingSlotsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_parking_slots_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ParkingSlotsModel parkingSlotsModel = parkingSlotsModelList.get(position);

        holder.txtParkingNumber.setText(parkingSlotsModel.getpNumber());
        holder.txtStatus.setText(parkingSlotsModel.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (parkingSlotsModel.getStatus().equals("unavailable")){
                    Toast.makeText(context, "This slot is unavailable", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Hey!!! Welcome to the Reservation Section \n \t\t\t\t\t You want to Reserve Slot # "
                            +holder.getAdapterPosition() , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,BookingActivity.class);
                    intent.putExtra("parkingNumber",parkingSlotsModel.getpNumber());
                    context.startActivity(intent);
                }

            }
        });

        if (parkingSlotsModel.getStatus().equals("available")){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

    }

    @Override
    public int getItemCount() {
        return parkingSlotsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtParkingNumber,txtStatus;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtParkingNumber = itemView.findViewById(R.id.txtParkingNumber);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
