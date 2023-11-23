package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Results extends Activity {
    private TextView timeRB_1, timeRB_2, timeRB_3, timeRB_4, timeRB_5, timeS_1,timeS_2, timeS_3, timeS_4, timeS_5,
            timeSP_1, timeSP_2, timeSP_3, timeSP_4, timeSP_5;
    private TextView clickRB_1, clickRB_2, clickRB_3, clickRB_4, clickRB_5, clickS_1,clickS_2,clickS_3,clickS_4,clickS_5,
            clickSP_1,clickSP_2,clickSP_3,clickSP_4,clickSP_5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        // TODO get the values from bundle and set the textviews
        initialize();
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
}
