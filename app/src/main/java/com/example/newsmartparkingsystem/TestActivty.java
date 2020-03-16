package com.example.newsmartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newsmartparkingsystem.Adapter.ParkingSlotsAdapter;
import com.example.newsmartparkingsystem.Common.Common;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivty extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;

    List<ParkingSlotsModel>parkingSlotsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activty);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        reference = FirebaseDatabase.getInstance().getReference("Parking Slots");

        getData();


//        for (int i = 0 ; i < 7; i++){
//                HashMap<String, Object> hashMap = new HashMap<>();
//                hashMap.put("status", "available");
//                hashMap.put("pNumber", String.valueOf(i));
//                reference.child("Cavalary Ground").push().setValue(hashMap);
//        }

    }

    private void getData() {

        reference.child(Common.parkingTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ParkingSlotsModel parkingSlotsModel = snapshot.getValue(ParkingSlotsModel.class);
                    parkingSlotsModelList.add(parkingSlotsModel);

                }

                ParkingSlotsAdapter parkingSlotsAdapter = new ParkingSlotsAdapter(TestActivty.this,parkingSlotsModelList);
                recyclerView.setAdapter(parkingSlotsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TestActivty.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
