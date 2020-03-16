package com.example.newsmartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class BookingDetails extends AppCompatActivity {

    ImageView screenshot_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        screenshot_Img = findViewById(R.id.img_screenshot);

        screenshot_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshoot();
                Toast.makeText(BookingDetails.this, "Congratulations!!! Screenshoot has been \n \t \t\t\t\t\t \t\t\t\t\t taken", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookingDetails.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        String name = intent.getStringExtra("NAME");
        TextView textView = findViewById(R.id.txt_Name_afbooking);
        textView.setText(name);

        String phone = intent.getStringExtra("PHONE");
        TextView textView1 = findViewById(R.id.txt_phone_afbooking);
        textView1.setText(phone);

        String vNumber = intent.getStringExtra("VEHICLE_NUMBER");
        TextView textView2 = findViewById(R.id.txt_vehicleNumber_afbooking);
        textView2.setText(vNumber);

        String sCalender = intent.getStringExtra("DATE");
        TextView textView4 = findViewById(R.id.date_afbooking);
        textView4.setText(sCalender);

        String sEntryTime = intent.getStringExtra("ENTRYTIME");
        TextView textView3 = findViewById(R.id.entry_time_afbooking);
        textView3.setText(sEntryTime);

        String sExitTime = intent.getStringExtra("EXITTIME");
        TextView textView5 = findViewById(R.id.exit_time_afbooking);
        textView5.setText(sExitTime);

    }

    private void screenshoot() {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String filename = Environment.getExternalStorageDirectory() + "/ScreenShooter/" + now + ".jpg";

        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
