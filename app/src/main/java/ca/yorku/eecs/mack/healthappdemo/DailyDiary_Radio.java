package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


public class DailyDiary_Radio extends Activity {
    private TextView dayOfWeek;
    private RadioGroup energy, sleep, stress,exercise, meals;
    Button nextBtn;
    private final static String MYDEBUG = "MYDEBUG"; // for Log.i messages
    private long startTimeRBtn; // to store the start time
    private long elapsedTime; // to store elapsed time
    private int clickCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailydiary_radio_btn);
        Log.i(MYDEBUG, "Radio.");
        // TODO
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

    // Listener for RadioGroups
    private final RadioGroup.OnCheckedChangeListener radioGroupChangeListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
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
        long endTime = System.currentTimeMillis();
        // Calculate the elapsed time
        elapsedTime = (endTime - startTimeRBtn)/1000;
        Bundle b = new Bundle();
        b.putLong("trialTimeR", elapsedTime);
        // TODO update the activity
        Intent intent = new Intent(this, DailyDiary_Slider.class);
            startActivity(intent);
    }
}
