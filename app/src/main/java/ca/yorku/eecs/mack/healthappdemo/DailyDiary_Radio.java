package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;


public class DailyDiary_Radio extends Activity {
    private TextView dayOfWeek;
    private RadioGroup energy, sleep, stress,exercise, meals;
    Button nextBtn;
    private final static String MYDEBUG = "MYDEBUG"; // for Log.i messages
    private long startTimeRBtn; // to store the start time
    private double elapsedTime; // to store elapsed time
    private int clickCount = 0;
    int counter;
    private static final String PREFS_NAME = "RadioButton";
    private static final String TIME_KEY = "time";
    private static final String CLICK_COUNT_KEY = "click_count";
    private DecimalFormat df = new DecimalFormat("#.##");
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailydiary_radio_btn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i(MYDEBUG, "Radio.");
        counter = getCounter();
        dayOfWeek = findViewById(R.id.paramLabelOrder);
        dayOfWeek.setText(String.format("%d/5", counter));
        //get the day of the week from home screen and set textview with it
        //get the trial count and use that to save the results of time spent
        //check if selected
        energy = findViewById(R.id.optionsRadioGroup);
        sleep = findViewById(R.id.options);
        stress = findViewById(R.id.optionsst);
        exercise = findViewById(R.id.optionse);
        meals = findViewById(R.id.optionsn);
        nextBtn = findViewById(R.id.next);

        startTimeRBtn = System.currentTimeMillis();
        energy.setOnCheckedChangeListener(radioGroupChangeListener);
        sleep.setOnCheckedChangeListener(radioGroupChangeListener);
        stress.setOnCheckedChangeListener(radioGroupChangeListener);
        exercise.setOnCheckedChangeListener(radioGroupChangeListener);
        meals.setOnCheckedChangeListener(radioGroupChangeListener);
    }

    private int getCounter() {
        SharedPreferences prefs = getSharedPreferences(HealthAppDemo.PREFS, Context.MODE_PRIVATE);
        return prefs.getInt("counter", 1); // 1 is the default value
    }
    // Listener for RadioGroups
    private final RadioGroup.OnCheckedChangeListener radioGroupChangeListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                    clickCount++;
                    // Check if at least one option is selected in each group
                    boolean isGroup1Selected = energy.getCheckedRadioButtonId() != View.NO_ID;
                    boolean isGroup2Selected = sleep.getCheckedRadioButtonId() != View.NO_ID;
                    boolean isGroup3Selected = stress.getCheckedRadioButtonId() != View.NO_ID;
                    boolean isGroup4Selected = exercise.getCheckedRadioButtonId() != View.NO_ID;
                    boolean isGroup5Selected = meals.getCheckedRadioButtonId() != View.NO_ID;

                    // Enable the "Next" button if both groups have at least one option selected
                    nextBtn.setEnabled(isGroup1Selected && isGroup2Selected && isGroup3Selected
                            && isGroup4Selected && isGroup5Selected);
                }
            };

    // Listener for the "Next" button click
    public void onNextButtonClick(View view) {
        saveData();
        Bundle b = new Bundle();
        b.putInt("count", counter);
        Intent intent = new Intent(this, DailyDiary_Slider.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void saveData(){
        long endTime = System.currentTimeMillis();
        // Calculate the elapsed time
        elapsedTime = (endTime - startTimeRBtn)/1000.0;
        String time = df.format(elapsedTime);
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TIME_KEY+counter, time);
        editor.apply();
        editor.putInt(CLICK_COUNT_KEY+counter, clickCount);
        editor.apply();
    }
}
