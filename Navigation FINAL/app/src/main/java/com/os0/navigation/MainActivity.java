package com.os0.navigation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.os0.navigation.models.Memo;
import com.os0.navigation.models.MemoList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    float x1,x2,y1,y2;
    TextView memotv;
    TextView schedtv;
    TextView hourNav, minuteNav; //Digital Clock
    Button logoutBtn;
    MemoList list;
    SharedPreferences spChronic;
    SharedPreferences.Editor editorChronic;
    private long mssChronic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memotv = findViewById(R.id.memorytxt);
        schedtv = findViewById(R.id.scheduler);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);

        //Memo
        try
        {
            list = gson.fromJson(json, MemoList.class);
            if (list != null){
                if (list.isEmpty()) memotv.setText("Memory");
            }
            if(list.size() == 0){
                memotv.setText("Memory");
            }
            else {
                memotv.setText(list.get(0).tvText);
            }

        }
        catch (IllegalStateException | JsonSyntaxException exception) {

        }
        // Schedule
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        if(sp.contains("schedule list")) {
            String json2 = sp.getString("schedule list", null);
            Type type = new TypeToken<ArrayList<ScheduleItem>>(){}.getType();
            ArrayList<ScheduleItem> items = gson.fromJson(json2, type);
            String msg = "";
            if(items.size() == 0) {
                msg = "No schedules";
            } else {
                msg = items.get(0).toString();
            }
            schedtv.setText(msg);
        }

        //Clock Navigation
        hourNav = (TextView) findViewById(R.id.hourNav);
        minuteNav = (TextView) findViewById(R.id.minuteNav);

        spChronic = getSharedPreferences("Clock", MODE_PRIVATE);
        editorChronic = spChronic.edit();
        mssChronic = spChronic.getLong("mss", 0);
        if (mssChronic == 0) {
            editorChronic.putLong("mss", 0);
            editorChronic.apply();
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - mssChronic);
        hourNav.setText(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
        minuteNav.setText(String.valueOf(c.get(Calendar.MINUTE)));

        CustomAnalogClock AnalClock = (CustomAnalogClock) findViewById(R.id.AnalClock);
        AnalClock.setDiff(c.get(Calendar.HOUR_OF_DAY) - Calendar.getInstance().get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE) - Calendar.getInstance().get(Calendar.MINUTE), false);

        CountDownTimer cdt = new CountDownTimer(9000000, 1000) {
            @Override
            public void onTick(long l) {
                mssChronic = spChronic.getLong("mss", 0);
                c.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - mssChronic);
                hourNav.setText(String.format("%02d", c.get(Calendar.HOUR_OF_DAY)));
                minuteNav.setText(String.format("%02d", c.get(Calendar.MINUTE)));
                AnalClock.setDiff(c.get(Calendar.HOUR_OF_DAY) - Calendar.getInstance().get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE) - Calendar.getInstance().get(Calendar.MINUTE), false);
            }

            @Override
            public void onFinish() {

            }
        };

        cdt.start();

        //Login
        Button logoutBtn = (Button) findViewById(R.id.logoutBUTT);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefBoolean = getApplicationContext().getSharedPreferences("BooleanPref", MODE_PRIVATE);
                SharedPreferences.Editor editPref = prefBoolean.edit();
                editPref.putBoolean("isLoggedIn", false);
                editPref.apply();
                Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Schedule
        Gson gson = new Gson();
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        if(sp.contains("schedule list")) {
            String json2 = sp.getString("schedule list", null);
            Type type = new TypeToken<ArrayList<ScheduleItem>>(){}.getType();
            ArrayList<ScheduleItem> items = gson.fromJson(json2, type);
            String msg = "";
            if(items.size() == 0) {
                msg = "No schedules";
            } else {
                msg = items.get(0).toString();
            }
            schedtv.setText(msg);
        }

        //Memo
        try
        {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
            String json = sharedPreferences.getString("taskList", null);
            list = gson.fromJson(json, MemoList.class);
            if (list != null){
                if (list.isEmpty()) memotv.setText("Memory");
            }
            if(list.size() == 0){
                memotv.setText("Memory");
            }
            else {
                memotv.setText(list.get(0).tvText);
            }

        }
        catch (IllegalStateException | JsonSyntaxException exception) {

        }
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
                if (x1 > x2){
                    Intent i = new Intent(MainActivity.this, Navigation.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
