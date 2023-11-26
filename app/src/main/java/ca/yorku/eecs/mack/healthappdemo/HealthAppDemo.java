package ca.yorku.eecs.mack.healthappdemo;// MainActivity.java

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HealthAppDemo extends AppCompatActivity {
    public static final String PREFS = "MyPrefsFile"; // File to store the counter
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Get the TextView reference
        TextView todayDateTextView = findViewById(R.id.todayDateTextView);

        // Get today's date
        String todayDate = getCurrentDate();

        // Set the text of todayDateTextView to display today's date
        todayDateTextView.setText(todayDate);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of exercises (replace this with your actual data)
        List<String> exerciseList = new ArrayList<>();
        exerciseList.add("• Mix it up: Combine cardio and strength training for a well-rounded fitness routine.");
        exerciseList.add("• Flexibility matters: Include yoga or Pilates for improved mobility.");
        exerciseList.add("• Consistency beats intensity: Regular, moderate exercise is key.");
        exerciseList.add("• Function over form: Prioritize exercises that mimic daily movements.");
        exerciseList.add("• Listen to your body: Adjust workouts based on how you feel.");
        // Add more exercises as needed

        // Create and set the adapter
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, exerciseList);
        recyclerView.setAdapter(exerciseAdapter);
    }

    // Helper method to get today's date
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
    public void onDailyDiaryButtonClick(View view){
        /*SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int counter = prefs.getInt("counter", 1);
        if (counter > 5){
            //show normal questionnaire, no more trials
        }*/
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
        Intent intent = new Intent(this, DailyDiary_Radio.class);
        startActivity(intent);
    }

    public void onWellnessGoalsClick(View view){
        /*SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int counter = prefs.getInt("counter", 1);
        if (counter > 5){
            //show normal questionnaire, no more trials
        }*/
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
        Intent intent = new Intent(this, WellnessGoalsActivity.class);
        startActivity(intent);
    }
}