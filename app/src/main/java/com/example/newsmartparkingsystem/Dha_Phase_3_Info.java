package com.example.newsmartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Dha_Phase_3_Info extends AppCompatActivity {
    ViewFlipper v_flipper;
    Button booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dha_phase_3);
        int images[] = {R.drawable.dha1, R.drawable.dha2,R.drawable.dha3};

        v_flipper = findViewById(R.id.v_flipper);

        booking = findViewById(R.id.btn_booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dha_Phase_3_Info.this , TestActivty.class);
                startActivity(intent);
            }
        });
        //For Loop

        /*for (int i=0; i<images.length; i++){
            flipperImages(images[i]);

        }*/

        for (int image : images) {
            flipperImages(image);
        }
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);// 4 sec
        v_flipper.setAutoStart(true);

        //animation

        v_flipper.setInAnimation(this , android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this , android.R.anim.slide_out_right);
    }
}
