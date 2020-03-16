package com.example.newsmartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsmartparkingsystem.Adapter.HistorySlotsAdapter;
import com.example.newsmartparkingsystem.Adapter.ParkingSlotsAdapter;
import com.example.newsmartparkingsystem.Common.Common;
import com.example.newsmartparkingsystem.Model.HistorySlotsModel;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity{

    DatabaseReference reference;
    RecyclerView recyclerView;
    TextView history;

    List<HistorySlotsModel> historySlotsModelList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history = findViewById(R.id.txtHistory);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        reference = FirebaseDatabase.getInstance().getReference("Booking History")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    HistorySlotsModel historySlotsModel = snapshot.getValue(HistorySlotsModel.class);
                    historySlotsModelList.add(historySlotsModel);
                }

                HistorySlotsAdapter historySlotsAdapter = new HistorySlotsAdapter(HistoryActivity.this,historySlotsModelList);
                recyclerView.setAdapter(historySlotsAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(HistoryActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
