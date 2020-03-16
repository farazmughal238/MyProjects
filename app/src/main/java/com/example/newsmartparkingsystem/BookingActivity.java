package com.example.newsmartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsmartparkingsystem.Common.Common;
import com.example.newsmartparkingsystem.Model.ParkingSlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener ,
        TimePickerDialog.OnTimeSetListener {

    private DatePickerDialog dpd;
    private TimePickerDialog tpd;
    boolean buttonClicked = true;

    Calendar now;
    Date selectedDate;

    Button confirm_booking;


    EditText booking_name, booking_phone, vehicle_number;
    ImageView Calender, Entry_Time , Exit_Time;
    TextView txtCalender , txtEntryTime, txtExitTime;


    String pNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        pNumber = getIntent().getStringExtra("parkingNumber");

        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                BookingActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
        dpd.setMinDate(now);

        //EditText
        booking_name= findViewById(R.id.edt_name_bfbooking);
        booking_phone=findViewById(R.id.edt_phone_bfbooking);
        vehicle_number=findViewById(R.id.edt_vehicleNumber_bfbooking);

        //ImageView

        Calender = findViewById(R.id.img_calender);
        Entry_Time = findViewById(R.id.img_clock_entry);
        Exit_Time = findViewById(R.id.img_clock_exit);

        //TextView

        txtCalender = findViewById(R.id.txtCalender);
        txtEntryTime = findViewById(R.id.txt_entry_time);
        txtExitTime = findViewById(R.id.txt_exit_time);

        //Button

        confirm_booking = findViewById(R.id.btn_confirm_booking);

        //Listener on booking button

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = findViewById(R.id.edt_name_bfbooking);
                String sName = name.getText().toString();

                EditText phone = findViewById(R.id.edt_phone_bfbooking);
                String sPhone = phone.getText().toString();

                EditText vNumber = findViewById(R.id.edt_vehicleNumber_bfbooking);
                String sVehicleNumber = vNumber.getText().toString();

                TextView txtCalender = findViewById(R.id.txtCalender);
                String sCalender = txtCalender.getText().toString();

                TextView entry_time = findViewById(R.id.txt_entry_time);
                String sEntryTime = entry_time.getText().toString();

                TextView exit_time = findViewById(R.id.txt_exit_time);
                String sExitTime = exit_time.getText().toString();



                changeStatus(sName,sPhone,sVehicleNumber,sCalender,sEntryTime,sExitTime);



            }
        });


        Calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();

            }
        });
        Entry_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = true;
                selectEntryTime();

            }
        });

        Exit_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = false;
                selectEntryTime();

            }
        });

    }

    private void changeStatus(final String sName, final String sPhone, final String sVehicleNumber,
                              final String sCalender, final String sEntryTime, final String sExitTime) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parking Slots");
        reference.child(Common.parkingTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ParkingSlotsModel parkingSlotsModel = snapshot.getValue(ParkingSlotsModel.class);

                    if (parkingSlotsModel.getpNumber().equals(pNumber)){

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("pNumber",parkingSlotsModel.getpNumber());
                        hashMap.put("status","unavailable");

                        snapshot.getRef().setValue(hashMap);
                        addIntoBookingHistory(parkingSlotsModel.getpNumber(),sName,sPhone,sVehicleNumber,sCalender,
                                sEntryTime,sExitTime);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addIntoBookingHistory(String getpNumber,String sName, String sPhone, String sVehicleNumber,
                                       String sCalender, String sEntryTime, String sExitTime) {
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Booking History");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", Common.parkingTitle);
        hashMap.put("status", "unavailable");
        hashMap.put("pNumber", pNumber);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(hashMap);

        Intent intent = new Intent(BookingActivity.this , BookingDetails.class);
        intent.putExtra("NAME" , sName);
        intent.putExtra("PHONE" , sPhone);
        intent.putExtra("VEHICLE_NUMBER", sVehicleNumber);
        intent.putExtra("ENTRYTIME", sEntryTime);
        intent.putExtra("EXITTIME",sExitTime);
        intent.putExtra("DATE", sCalender);
        startActivity(intent);
    }

    private void selectDate() {
        dpd.setThemeDark(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    private void selectEntryTime(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();



        if (selectedDate != null) {
            if (selectedDate.after(currentDate)) {


                tpd = TimePickerDialog.newInstance(BookingActivity.this, calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);

            } else {
                tpd = TimePickerDialog.newInstance(BookingActivity.this, calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
                tpd.setMinTime(currentDate.getHours(), currentDate.getMinutes(), currentDate.getSeconds());
            }
            tpd.setThemeDark(true);
            tpd.setVersion(TimePickerDialog.Version.VERSION_1);
            tpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
            tpd.show(getFragmentManager(), "TimePickerDialog");



        } else {
            Toast.makeText(BookingActivity.this, "You must select the Date first", Toast.LENGTH_SHORT).show();
        }






    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String dayOfTheWeek;
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        selectedDate = now.getTime();
        dayOfTheWeek = (String) DateFormat.format("EEEE", selectedDate);


        txtCalender.setText(sdf.format(selectedDate));

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {


        if(buttonClicked){

            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            Time tme = new Time(hourOfDay, minute, 0);//seconds by default set to zero
            Format formatter;
            formatter = new SimpleDateFormat("h:mm a");

        txtEntryTime.setText(formatter.format(tme));

            buttonClicked = false;
        }

        else {
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            Time tme = new Time(hourOfDay, minute, 0);//seconds by default set to zero
            Format formatter;
            formatter = new SimpleDateFormat("h:mm a");
            txtExitTime.setText(formatter.format(tme));

        }
    }

}
