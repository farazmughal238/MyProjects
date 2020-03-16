package com.example.newsmartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivty extends AppCompatActivity {

    @BindView(R.id.splash_image) ImageView mLogo;
    LinearLayout descimage,desctxt;
    Animation uptodown,downtoup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        ButterKnife.bind(this);

        descimage = (LinearLayout) findViewById(R.id.titleimage);
        desctxt = (LinearLayout) findViewById(R.id.titletxt);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);

        descimage.setAnimation(downtoup);
        desctxt.setAnimation(uptodown);

        RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());
        //mLogo.startAnimation(rotate);

        Thread th=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivty.this,MainActivity.class);
                    startActivity(intent);

                    //checkUserAlreadyExits();




                }
            }
        };
        th.start();
    }

    private void checkUserAlreadyExits() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(SplashActivty.this,MapsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashActivty.this,MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

