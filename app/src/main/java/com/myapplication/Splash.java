package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    Animation Spin,top,bottom,blink;
    ImageView spImg;
    TextView sptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Spin= AnimationUtils.loadAnimation(this,R.anim.spin);
        top=AnimationUtils.loadAnimation(this,R.anim.top);
        bottom=AnimationUtils.loadAnimation(this,R.anim.bottom);
        sptext=findViewById(R.id.splashtext);
        spImg=findViewById(R.id.spimg);
        blink=AnimationUtils.loadAnimation(this,R.anim.blink);

        spImg.setAnimation(top);
        spImg.setAnimation(blink);
        sptext.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(Splash.this, MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.bottom);
                finish();

            }
        },3000);
    }
}