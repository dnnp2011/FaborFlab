package com.sleepless_entertainment.drowsy.faborflab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static SharedPreferences preferences;

    public static final String EXTRA_ITEM_TITLE = "exercise.background.type";
    public static final String PREF_FILE_NAME = "workout_completion";

    public static final String EXERCISE_WEIGHTS = "Weight Lifting";
    public static final String EXERCISE_YOGA = "Yoga";
    public static final String EXERCISE_CARDIO = "Cardio";

    public static Integer WeightCompletion = 0;
    public static Integer YogaCompletion = 0;
    public static Integer CardioCompletion = 0;

//    public static SharedPreferences preferences;

    private TextView weightCompletionView;
    private TextView yogaCompletionView;
    private TextView cardioCompletionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightCompletionView = findViewById(R.id.w_percentageCompleteView);
        yogaCompletionView = findViewById(R.id.y_percentageCompleteView);
        cardioCompletionView = findViewById(R.id.c_percentageCompleteView);

        preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPrefValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPrefValues();
        setCompletionLabels();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePrefValues();
    }

    //region Helper Methods
    private void loadDetailActivity(String exercise) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(EXTRA_ITEM_TITLE, exercise);
        startActivity(intent);
    }

    private void setCompletionLabels() {
        weightCompletionView.setText(String.format("%d%%", WeightCompletion));
        yogaCompletionView.setText(String.format("%d%%", YogaCompletion));
        cardioCompletionView.setText(String.format("%d%%", CardioCompletion));
    }

    public static void loadPrefValues() {
        assert preferences != null;

        WeightCompletion = preferences.getInt("weightCompletion", 0);
        YogaCompletion = preferences.getInt("yogaCompletion", 0);
        CardioCompletion = preferences.getInt("cardioCompletion", 0);
    }

    public static void savePrefValues() {
        assert preferences != null;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("weightCompletion", WeightCompletion);
        editor.putInt("yogaCompletion", YogaCompletion);
        editor.putInt("cardioCompletion", CardioCompletion);
        editor.apply();
    }
    //endregion

    //region Button Listeners
    public void onClickWeightlifting(View view) {
        loadDetailActivity(EXERCISE_WEIGHTS);
    }

    public void onClickYoga(View view) {
        loadDetailActivity(EXERCISE_YOGA);
    }

    public void onClickCardio(View view) {
        loadDetailActivity(EXERCISE_CARDIO);
    }
    //endregion
}
