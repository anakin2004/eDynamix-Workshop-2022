package com.os0.navigation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Navigation extends AppCompatActivity {

    float x1, x2, y1, y2;

    Button Bchat;
    Button Bphone;
    Button Bvideo;
    Button Bcamera;
    Button Bgallery;
    Button Bbrowser;
    Button Bshed;
    Button Breminder;
    Button Bcalculator;
    Button Bclock;
    Button Bcalendar;
    Button BTicTackToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bchat = (Button) findViewById(R.id.chat);
        Bphone = (Button) findViewById(R.id.phone);
        Bvideo = (Button) findViewById(R.id.video);
        Bcamera = (Button) findViewById(R.id.camera);
        Bgallery = (Button) findViewById(R.id.gallery);
        Bbrowser = (Button) findViewById(R.id.browser);
        Bshed = (Button) findViewById(R.id.shed);
        Breminder = (Button) findViewById(R.id.reminder);
        Bcalculator = (Button) findViewById(R.id.calculator);
        Bclock = (Button) findViewById(R.id.clock);
        Bcalendar = (Button) findViewById(R.id.calendar);
        BTicTackToe = (Button) findViewById(R.id.TicTackToe);

        Bchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, Messenger.class);
                startActivity(i);
            }
        });

        Bphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, paint_MainActivity.class);
                startActivity(i);
            }
        });

        Bvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, YouTube_MainAct.class);
                startActivity(i);
            }
        });

        Bcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, camera_MainActivity.class);
                startActivity(i);
            }
        });

        Bgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, camera_GalleryActivity.class);
                startActivity(i);
            }
        });

        Bbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, web_BrowserMain.class);
                startActivity(i);
            }
        });

        Bshed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(Navigation.this, ContactsActivityMain.class);
                startActivity(i);
            }

        });

        Breminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, Memory.class);
                startActivity(i);
            }
        });

        Bcalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, calc_MainActivity.class);
                startActivity(i);
            }
        });

        Bclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, Clock.class);
                startActivity(i);
            }
        });

        Bcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, SchedulerMainActivity.class);
                startActivity(i);
            }
        });

        BTicTackToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Navigation.this, TicTakToe.class);
                startActivity(i);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2) {
                    Intent i = new Intent(Navigation.this, MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}