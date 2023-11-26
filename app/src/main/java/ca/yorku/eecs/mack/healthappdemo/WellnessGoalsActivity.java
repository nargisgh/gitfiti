package ca.yorku.eecs.mack.healthappdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WellnessGoalsActivity extends Activity {

    private static final String PREF_NAME = "WellnessGoalsPref";
    private static final String GOALS_KEY = "Goals";

    private LinearLayout goalsLayout;
    private Button buttonAddGoal;
    private Button buttonSaveGoals;
    private Button buttonReset;
    private TextView batteryStatusTextView;

    private boolean isFirstGoalCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_goals);

        goalsLayout = findViewById(R.id.goalsLayout);
        buttonAddGoal = findViewById(R.id.buttonAddGoal);
        buttonSaveGoals = findViewById(R.id.buttonSaveGoals);
        buttonReset = findViewById(R.id.buttonReset);
        batteryStatusTextView = findViewById(R.id.batteryStatusTextView);

        TextView textViewHeader = findViewById(R.id.textViewHeader);
        textViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreatingGoal();
            }
        });

        // Check if goals are already created, and update button visibility accordingly
        if (goalsLayout.getChildCount() == 0) {
            buttonAddGoal.setVisibility(View.VISIBLE); // Show the central "+" button
            buttonSaveGoals.setVisibility(View.GONE); // Hide the "Save Goals" button initially
        } else {
            buttonAddGoal.setVisibility(View.GONE); // Hide the central "+" button
            buttonSaveGoals.setVisibility(View.VISIBLE); // Show the "Save Goals" button
        }

        loadSavedGoals();

        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreatingGoal();
            }
        });

        buttonSaveGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGoals();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGoals();
            }
        });

        // Register a BroadcastReceiver to listen for battery changes
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        // Register a BroadcastReceiver to receive battery updates
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, intentFilter);
    }

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            // Calculate battery percentage
            float batteryPercentage = (level / (float) scale) * 100;

            // Update the battery status TextView
            updateBatteryStatus(batteryPercentage);
        }
    };

    private void updateBatteryStatus(float batteryPercentage) {
        // Update the battery status TextView with the battery percentage
        String batteryStatus = "Battery Status: " + batteryPercentage + "%";
        batteryStatusTextView.setText(batteryStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the battery receiver when the activity is destroyed
        unregisterReceiver(batteryReceiver);
    }

    private void saveGoals() {
        int goalCount = goalsLayout.getChildCount();
        if (goalCount > 0) {
            StringBuilder goalsStringBuilder = new StringBuilder();

            // Iterate through the goals and append them to the StringBuilder
            for (int i = 0; i < goalCount; i++) {
                View childView = goalsLayout.getChildAt(i);
                if (childView instanceof TextView) {
                    String goalText = ((TextView) childView).getText().toString();
                    goalsStringBuilder.append(goalText);

                    // Add a newline character to separate each goal
                    goalsStringBuilder.append("\n");
                }
            }

            // Save goals using SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(GOALS_KEY, goalsStringBuilder.toString());
            editor.apply();

            // Show the "Save Goals" dialog in the middle of the screen
            showSaveGoalsDialog(goalsStringBuilder.toString());
        } else {
            Toast.makeText(this, "No goals to save", Toast.LENGTH_SHORT).show();

            // If there are no goals, hide the "Save Goals" button
            buttonSaveGoals.setVisibility(View.GONE);
        }
    }

    private void loadSavedGoals() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedGoals = sharedPreferences.getString(GOALS_KEY, "");

        // Clear the current goals before adding the saved ones
        goalsLayout.removeAllViews();

        if (!savedGoals.isEmpty()) {
            // Split the saved goals by newline character
            String[] goalsArray = savedGoals.split("\n");

            // Display each saved goal
            for (String goal : goalsArray) {
                addGoal(goal);
            }

            // Update button visibility accordingly
            buttonSaveGoals.setVisibility(View.VISIBLE);
        } else {
            // If there are no saved goals, show the central "+" button
            buttonSaveGoals.setVisibility(View.GONE); // Hide the "Save Goals" button
        }

        // Always show the "+" button
        buttonAddGoal.setVisibility(View.VISIBLE);

        TextView textViewHeader = findViewById(R.id.textViewHeader);
        textViewHeader.setText("Create Wellness Goals");
        isFirstGoalCreated = false;
    }

    private void startCreatingGoal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Wellness Goal");

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newGoal = input.getText().toString().trim();
                if (!newGoal.isEmpty()) {
                    addGoal(newGoal);

                    // Show the "Save Goals" button after a goal is inputted
                    buttonSaveGoals.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(WellnessGoalsActivity.this, "Goal cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addGoal(final String goalText) {
        // Create a new TextView for the goal
        final TextView goalTextView = new TextView(this);
        goalTextView.setText(goalText);
        goalTextView.setTextSize(18);

        // Set text color, style, and size
        goalTextView.setTextColor(Color.WHITE);
        goalTextView.setTypeface(null, Typeface.BOLD);
        goalTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        // Add a click listener for editing/deleting the goal
        goalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditOptions(goalTextView);
            }
        });

        // Add spacing between goals
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, dpToPx(1)); // 1dp bottom margin

        // Add the TextView to the goalsLayout
        goalsLayout.addView(goalTextView, layoutParams);

        // Show the central "+" button after a goal is added
        buttonAddGoal.setVisibility(View.VISIBLE);

        // If it's the first goal, update the header
        if (!isFirstGoalCreated) {
            TextView textViewHeader = findViewById(R.id.textViewHeader);
            textViewHeader.setText("Wellness Goals:");
            isFirstGoalCreated = true;
        }

        // Check if goals are created, and update "Save Goals" button visibility accordingly
        if (goalsLayout.getChildCount() == 1) {
            buttonSaveGoals.setVisibility(View.GONE); // Hide the "Save Goals" button
        }
        updateTodayGoalsTextView();
    }

    private void updateTodayGoalsTextView() {
        // Find the TextView with the id textView4
        TextView todayGoalsTextView = findViewById(R.id.textView4);

        // Check if the TextView exists
        if (todayGoalsTextView != null) {
            // Update the text of the TextView with the goals header
            todayGoalsTextView.setText("Today's Goals:");
        }
    }

    private void showEditOptions(final TextView goalTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Goal Options");

        builder.setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        editGoal(goalTextView);
                        break;
                    case 1:
                        deleteGoal(goalTextView);
                        break;
                }
            }
        });

        builder.show();
    }

    private void editGoal(final TextView goalTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Goal");

        final EditText input = new EditText(this);
        input.setText(goalTextView.getText().toString().trim());

        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedGoal = input.getText().toString().trim();
                if (!editedGoal.isEmpty()) {
                    goalTextView.setText(editedGoal);
                } else {
                    Toast.makeText(WellnessGoalsActivity.this, "Goal cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteGoal(final TextView goalTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Goal");

        builder.setMessage("Are you sure you want to delete this goal?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the goal from the layout
                goalsLayout.removeView(goalTextView);

                // If there are no more goals, show the central "+" button
                if (goalsLayout.getChildCount() == 0) {
                    buttonAddGoal.setVisibility(View.VISIBLE);

                    // Reset the header if there are no more goals
                    TextView textViewHeader = findViewById(R.id.textViewHeader);
                    textViewHeader.setText("Create Wellness Goals");
                    isFirstGoalCreated = false;

                    // Hide the "Save Goals" button
                    buttonSaveGoals.setVisibility(View.GONE);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showSaveGoalsDialog(String goalsText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Goals");

        final TextView message = new TextView(this);
        message.setText(goalsText);
        message.setPadding(16, 8, 16, 8); // Adjust padding as needed

        builder.setView(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetGoals() {
        // Clear all goals
        goalsLayout.removeAllViews();

        // Reset header and button visibility
        TextView textViewHeader = findViewById(R.id.textViewHeader);
        textViewHeader.setText("Create Wellness Goals");
        isFirstGoalCreated = false;

        buttonAddGoal.setVisibility(View.VISIBLE);
        buttonSaveGoals.setVisibility(View.GONE);

        // Clear saved goals from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(GOALS_KEY);
        editor.apply();

        // Inform the user that wellness goals have been reset
        Toast.makeText(this, "Wellness goals reset", Toast.LENGTH_SHORT).show();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}