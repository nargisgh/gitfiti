package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Results extends Activity {
    private TextView timeRB_1, timeRB_2, timeRB_3, timeRB_4, timeRB_5, timeS_1,timeS_2, timeS_3, timeS_4, timeS_5,
            timeSP_1, timeSP_2, timeSP_3, timeSP_4, timeSP_5;
    private TextView clickRB_1, clickRB_2, clickRB_3, clickRB_4, clickRB_5, clickS_1,clickS_2,clickS_3,clickS_4,clickS_5,
            clickSP_1,clickSP_2,clickSP_3,clickSP_4,clickSP_5;

    private static final String PREFS_NAME = "RadioButton";
    private static final String PREFS_NAME2 = "Slider";
    private static final String PREFS3 = "List";
    private static final String TIME_KEY = "time";
    private static final String CLICK_COUNT_KEY = "click_count";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialize();
        setResults();
    }

    private void setResults() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String timer1r = prefs.getString(TIME_KEY+1,"s");
        int click1r = prefs.getInt(CLICK_COUNT_KEY+1, 0);
        String timer2r = prefs.getString(TIME_KEY+2,"s");
        int click2r = prefs.getInt(CLICK_COUNT_KEY+2, 0);
        String timer3r = prefs.getString(TIME_KEY+3,"s");
        int click3r = prefs.getInt(CLICK_COUNT_KEY+3, 0);
        String timer4r = prefs.getString(TIME_KEY+4,"s");
        int click4r = prefs.getInt(CLICK_COUNT_KEY+4, 0);
        String timer5r = prefs.getString(TIME_KEY+5,"s");
        int click5r = prefs.getInt(CLICK_COUNT_KEY+5, 0);

        SharedPreferences prefs2 = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        String timer1s = prefs2.getString(TIME_KEY+1,"s");
        int click1s = prefs2.getInt(CLICK_COUNT_KEY+1, 0);
        String timer2s = prefs2.getString(TIME_KEY+2,"s");
        int click2s = prefs2.getInt(CLICK_COUNT_KEY+2, 0);
        String timer3s = prefs2.getString(TIME_KEY+3,"s");
        int click3s = prefs2.getInt(CLICK_COUNT_KEY+3, 0);
        String timer4s = prefs2.getString(TIME_KEY+4,"s");
        int click4s = prefs2.getInt(CLICK_COUNT_KEY+4, 0);
        String timer5s = prefs2.getString(TIME_KEY+5,"s");
        int click5s = prefs2.getInt(CLICK_COUNT_KEY+5, 0);

        SharedPreferences prefs3 = getSharedPreferences(PREFS3, Context.MODE_PRIVATE);
        String timer1sp = prefs3.getString(TIME_KEY+1,"s");
        int click1sp = prefs3.getInt(CLICK_COUNT_KEY+1, 0);
        String timer2sp = prefs3.getString(TIME_KEY+2,"s");
        int click2sp = prefs3.getInt(CLICK_COUNT_KEY+2, 0);
        String timer3sp = prefs3.getString(TIME_KEY+3,"s");
        int click3sp = prefs3.getInt(CLICK_COUNT_KEY+3, 0);
        String timer4sp = prefs3.getString(TIME_KEY+4,"s");
        int click4sp = prefs3.getInt(CLICK_COUNT_KEY+4, 0);
        String timer5sp = prefs3.getString(TIME_KEY+5,"s");
        int click5sp = prefs3.getInt(CLICK_COUNT_KEY+5, 0);

        timeRB_1.setText(""+timer1r+" seconds");
        clickRB_1.setText(""+click1r);
        timeS_1.setText(""+timer1s+" seconds");
        clickS_1.setText(""+click1s);
        timeSP_1.setText(""+timer1sp+" seconds");
        clickSP_1.setText(""+click1sp);
        timeRB_2.setText(""+timer2r+" seconds");
        clickRB_2.setText(""+click2r);
        timeS_2.setText(""+timer2s+" seconds");
        clickS_2.setText(""+click2s);
        timeSP_2.setText(""+timer2sp+" seconds");
        clickSP_2.setText(""+click2sp);

        timeRB_3.setText(""+timer3r+" seconds");
        clickRB_3.setText(""+click3r);
        timeS_3.setText(""+timer3s+" seconds");
        clickS_3.setText(""+click3s);
        timeSP_3.setText(""+timer3sp+" seconds");
        clickSP_3.setText(""+click3sp);
        timeRB_4.setText(""+timer4r+" seconds");
        clickRB_4.setText(""+click4r);
        timeS_4.setText(""+timer4s+" seconds");
        clickS_4.setText(""+click4s);
        timeSP_4.setText(""+timer4sp+" seconds");
        clickSP_4.setText(""+click4sp);
        timeRB_5.setText(""+timer5r+" seconds");
        clickRB_5.setText(""+click5r);
        timeS_5.setText(""+timer5s+" seconds");
        clickS_5.setText(""+click5s);
        timeSP_5.setText(""+timer5sp+" seconds");
        clickSP_5.setText(""+click5sp);
    }

    private void initialize() {
        timeRB_1 = findViewById(R.id.timer1);
        timeRB_2 = findViewById(R.id.timer2);
        timeRB_3 = findViewById(R.id.timer3);
        timeRB_4 = findViewById(R.id.timer4);
        timeRB_5 = findViewById(R.id.timer5);

        timeS_1 = findViewById(R.id.times1);
        timeS_2 = findViewById(R.id.times2);
        timeS_3 = findViewById(R.id.time_s3);
        timeS_4 = findViewById(R.id.times4);
        timeS_5 = findViewById(R.id.times5);

        timeSP_1 = findViewById(R.id.timesp1);
        timeSP_2 = findViewById(R.id.timesp2);
        timeSP_3 = findViewById(R.id.timesp3);
        timeSP_4 = findViewById(R.id.timesp4);
        timeSP_5 = findViewById(R.id.timesp5);

        clickRB_1 = findViewById(R.id.clickr1);
        clickRB_2 = findViewById(R.id.clickr2);
        clickRB_3 = findViewById(R.id.clickr3);
        clickRB_4 = findViewById(R.id.clickr4);
        clickRB_5 = findViewById(R.id.clickr5);

        clickS_1 = findViewById(R.id.clicks1);
        clickS_2 = findViewById(R.id.clicks2);
        clickS_3 = findViewById(R.id.clicks3);
        clickS_4 = findViewById(R.id.clicks4);
        clickS_5 = findViewById(R.id.clicks5);

        clickSP_1 = findViewById(R.id.clicksp1);
        clickSP_2 = findViewById(R.id.clicksp2);
        clickSP_3 = findViewById(R.id.clicksp3);
        clickSP_4 = findViewById(R.id.clicksp4);
        clickSP_5 = findViewById(R.id.clicksp5);
    }

    public void onHomeButtonClick(View view){
        Intent intent;
        intent = new Intent(this, HealthAppDemo.class);
        startActivity(intent);
    }
}
