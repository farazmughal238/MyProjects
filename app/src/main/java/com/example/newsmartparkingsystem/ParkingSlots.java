package com.example.newsmartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newsmartparkingsystem.Adapter.ParkingSlotsAdapter;
import com.example.newsmartparkingsystem.Common.Common;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParkingSlots extends AppCompatActivity {

    RecyclerView recyclerViewParkingSlots;
    List<ParkingSlotsModel> parkingSlotsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slots);

        String title = getIntent().getStringExtra("title");
        Common.parkingTitle = title;

        recyclerViewParkingSlots = findViewById(R.id.recyclerViewParkingSlots);
        recyclerViewParkingSlots.setHasFixedSize(true);
        recyclerViewParkingSlots.setLayoutManager(new LinearLayoutManager(this));

        getParkingSlots(title);

    }

    private void getParkingSlots(String title) {

        parkingSlotsModelList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parking Slots");
        reference.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ParkingSlotsModel parkingSlotsModel = snapshot.getValue(ParkingSlotsModel.class);

                    parkingSlotsModelList.add(parkingSlotsModel);

                }

                ParkingSlotsAdapter parkingSlotsAdapter = new ParkingSlotsAdapter(ParkingSlots.this,parkingSlotsModelList);
                recyclerViewParkingSlots.setAdapter(parkingSlotsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
