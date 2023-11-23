package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class DailyDiary_Slider extends Activity {
    private TextView dayOfWeek;
    private SeekBar water, screenTime, sleep,energy, mindfulness;
    private Button nextBtn;
    private long startTimeSBtn; // to store the start time
    private long elapsedTime; // to store elapsed time
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailydiary_slide_btn);
        //get the day of the week from home screen and set textview with it
        //get the trial count and use that to save the results of time spent
        //check if selected
        water = findViewById(R.id.sliderSeekBar);
        screenTime = findViewById(R.id.sliderSeekBar2);
        sleep = findViewById(R.id.sliderSeekBar1);
        energy = findViewById(R.id.sliderSeekBar3);
        mindfulness = findViewById(R.id.sliderSeekBar4);
        nextBtn = findViewById(R.id.next);
        // Record the start time when the activity is created
        startTimeSBtn = System.currentTimeMillis();

        water.setOnSeekBarChangeListener(seekBarChangeListener);
        screenTime.setOnSeekBarChangeListener(seekBarChangeListener);
        sleep.setOnSeekBarChangeListener(seekBarChangeListener);
        energy.setOnSeekBarChangeListener(seekBarChangeListener);
        mindfulness.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    // Listener for SeekBars
    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Check if at least one SeekBar is not at position zero
                    boolean isAnySeekBarNotAtZero = water.getProgress() > 0 && screenTime.getProgress() > 0
                            && sleep.getProgress() > 0 && energy.getProgress() > 0 && mindfulness.getProgress() > 0;
                    // Enable the "Next" button if SeekBar is not at position zero
                    nextBtn.setEnabled(isAnySeekBarNotAtZero);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };
    public void onNextButtonClick(View view) {
        long endTime = System.currentTimeMillis();
        // Calculate the elapsed time
        elapsedTime = (endTime - startTimeSBtn)/1000;
        Bundle b = new Bundle();
        b.putLong("trialTime", elapsedTime);
        Intent intent = new Intent(this, HealthAppDemo.class);
        startActivity(intent);
    }
}
