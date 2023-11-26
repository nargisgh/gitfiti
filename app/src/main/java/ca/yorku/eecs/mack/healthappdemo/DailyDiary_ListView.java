package ca.yorku.eecs.mack.healthappdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class DailyDiary_ListView extends HealthAppDemo {
    private final static String MYDEBUG = "MYDEBUG";
    private Button nextButton;
    private TextView dailyLog, day, input, mood, mind, excercise;
    private long startTimeRBtn; // to store the start time
    private double elapsedTime; // to store elapsed time
    private int clickCount = 0;
    int counter;
    private static final String PREFS3 = "List";
    private static final String TIME_KEY = "time";
    private static final String CLICK_COUNT_KEY = "click_count";
    private DecimalFormat df = new DecimalFormat("#.##");
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);

    ListView list1, list2, list3;
    String moodsList[] = {"Happy", "Content", "Neutral", "Sad", "Stressed", "Anxious", "Other"};
    String mindsList[] = {"Brain fog", "Stressed", "Focused", "Distracted", "Calm", "Motivated", "Unproductive"};
    String excercisesList[] = {"Running", "Walking", "Cycling", "Weight Lifting", "Yoga", "Sports", "Other"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailydiary_listview_btn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle b = getIntent().getExtras();
        assert b != null;
        counter = b.getInt("count");
        startTimeRBtn = System.currentTimeMillis();

        list1 = findViewById(R.id.moodList);
        ArrayAdapter<String> moods;
        moods = new ArrayAdapter<String>(DailyDiary_ListView.this, R.layout.custom_listview, moodsList);
        list1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list1.setAdapter(moods);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String items = (String) adapterView.getItemAtPosition(i);
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                clickCount++;
            }
        });

        list2 = findViewById(R.id.mindList);
        ArrayAdapter<String> minds;
        minds = new ArrayAdapter<String>(DailyDiary_ListView.this, R.layout.custom_listview, mindsList);
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list2.setAdapter(minds);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String items = (String) adapterView.getItemAtPosition(i);
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                clickCount++;
            }
        });

        list3 = findViewById(R.id.excerciseList);
        ArrayAdapter<String> excercises;
        excercises = new ArrayAdapter<String>(DailyDiary_ListView.this, R.layout.custom_listview, excercisesList);
        list3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list3.setAdapter(excercises);
        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String items = (String) adapterView.getItemAtPosition(i);
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                clickCount++;
            }
        });

        initialize();
        day.setText("Day of the Week Log:                    "+counter+"/5");
        Log.i(MYDEBUG, "Initialization done. Application running.");

    }

    private void initialize() {
        // get references to buttons and text view from the layout manager (rather than instantiate them)
        nextButton = findViewById(R.id.nextButton);
        dailyLog = findViewById(R.id.dailyLog);
        day = findViewById(R.id.day);
        input = findViewById(R.id.input);
    }

    public void onNextClick(View view) {
        // Check if at least one item is selected in each list
        boolean isMoodSelected = isAtLeastOneItemSelected(list1);
        boolean isMindSelected = isAtLeastOneItemSelected(list2);
        boolean isExerciseSelected = isAtLeastOneItemSelected(list3);

        if (isMoodSelected && isMindSelected && isExerciseSelected) {
            // Only proceed if at least one item is selected in each list
            saveData();
            saveCounter();
            Intent intent;
            if (counter == 5) {
                intent = new Intent(this, Results.class);
            } else {
                intent = new Intent(this, HealthAppDemo.class);
            }
            startActivity(intent);
        } else {
            // Inform the user that they need to select at least one item in each list
            Toast.makeText(this, "Please select at least one item in each list", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAtLeastOneItemSelected(ListView listView) {
        // Check if at least one item is selected in the list
        int selectedItemCount = listView.getCheckedItemCount();
        return selectedItemCount > 0;
    }

    private void saveData(){
        long endTime = System.currentTimeMillis();
        // Calculate the elapsed time
        elapsedTime = (endTime - startTimeRBtn)/1000.0;
        String time = df.format(elapsedTime);
        SharedPreferences.Editor editor = getSharedPreferences(PREFS3, Context.MODE_PRIVATE).edit();
        editor.putString(TIME_KEY+counter, time);
        editor.apply();
        editor.putInt(CLICK_COUNT_KEY+counter, clickCount);
        editor.apply();
    }

    private void saveCounter() {
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        // Update the counter in SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        int newcount = counter;
        newcount++;
        editor.putInt("counter", newcount);
        editor.apply();
    }
}