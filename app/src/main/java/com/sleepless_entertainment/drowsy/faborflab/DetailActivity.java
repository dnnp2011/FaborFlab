package com.sleepless_entertainment.drowsy.faborflab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Set;

public class DetailActivity extends Activity {

    public static SharedPreferences preferences;

    private static final int NUM_CHECKBOX = 5;
    private static String CurrentExerciseBG;

    private View weightInclude;
    private View yogaInclude;
    private View cardioInclude;

    private LinearLayout weightLinearLayout;
    private LinearLayout yogaLinearLayout;
    private LinearLayout cardioLinearLayout;

//    private HashMap<String, String> checkboxValues;
    private Set<String> checkboxValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CurrentExerciseBG = getIntent().getStringExtra("exercise.background.type");

        weightInclude = findViewById(R.id.weightLiftingBG);
        yogaInclude = findViewById(R.id.yogaBG);
        cardioInclude = findViewById(R.id.cardioBG);

        weightLinearLayout = weightInclude.findViewById(R.id.weightLinearLayout);
        yogaLinearLayout = yogaInclude.findViewById(R.id.yogaLinearLayout);
        cardioLinearLayout = cardioInclude.findViewById(R.id.cardioLinearLayout);

        preferences = getSharedPreferences(MainActivity.PREF_FILE_NAME, MODE_PRIVATE);

        loadBackground(CurrentExerciseBG);

//        TODO: Have up arrow pop up an exercise selection UI, to avoid reliance on NEXT button
//        TODO: Implement top menu button
//        TODO: Add unique checklist items
//        TODO: Ensure that activities return to view the same direction they left from
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCheckboxValues();
        setExerciseCompletion(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCheckboxValues();
    }

    //region Helper Methods
    private void saveCheckboxValues() {
        assert preferences != null;

        SharedPreferences.Editor editor = preferences.edit();

        for(int i = 0; i < weightLinearLayout.getChildCount(); i++) {
            if (weightLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) weightLinearLayout.getChildAt(i);
            editor.putBoolean(String.valueOf(child.getId()), child.isChecked());
        }

        for(int i = 0; i < yogaLinearLayout.getChildCount(); i++) {
            if (yogaLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) yogaLinearLayout.getChildAt(i);
            editor.putBoolean(String.valueOf(child.getId()), child.isChecked());
        }

        for(int i = 0; i < cardioLinearLayout.getChildCount(); i++) {
            if (cardioLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) cardioLinearLayout.getChildAt(i);
            editor.putBoolean(String.valueOf(child.getId()), child.isChecked());
        }

        editor.apply();
    }

    private void loadCheckboxValues() {
        assert preferences != null;

        for(int i = 0; i <weightLinearLayout.getChildCount(); i++) {
            if (weightLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) weightLinearLayout.getChildAt(i);
            child.setChecked(preferences.getBoolean(String.valueOf(child.getId()), false));
        }

        for(int i = 0; i <yogaLinearLayout.getChildCount(); i++) {
            if (yogaLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) yogaLinearLayout.getChildAt(i);
            child.setChecked(preferences.getBoolean(String.valueOf(child.getId()), false));
        }

        for(int i = 0; i <cardioLinearLayout.getChildCount(); i++) {
            if (cardioLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;
            CheckBox child = (CheckBox) cardioLinearLayout.getChildAt(i);
            child.setChecked(preferences.getBoolean(String.valueOf(child.getId()), false));
        }
    }

    private void loadBackground(String targetBackground) {
        assert weightInclude != null && yogaInclude != null && cardioInclude != null;

        switch(targetBackground) {
            case MainActivity.EXERCISE_WEIGHTS:
                weightInclude.setVisibility(View.VISIBLE);
                yogaInclude.setVisibility(View.GONE);
                cardioInclude.setVisibility(View.GONE);
                break;
            case MainActivity.EXERCISE_YOGA:
                weightInclude.setVisibility(View.GONE);
                yogaInclude.setVisibility(View.VISIBLE);
                cardioInclude.setVisibility(View.GONE);
                break;
            case MainActivity.EXERCISE_CARDIO:
                weightInclude.setVisibility(View.GONE);
                yogaInclude.setVisibility(View.GONE);
                cardioInclude.setVisibility(View.VISIBLE);
                break;
            default:
                System.out.println("ERROR-> DetailActivity.onCreate() --> Unable to resolve intended exercise background...");
                break;
        }

        CurrentExerciseBG = targetBackground;
    }

    private String getNextBackground(String currentBackground) {
        switch (currentBackground) {
            case MainActivity.EXERCISE_WEIGHTS:
                return MainActivity.EXERCISE_YOGA;
            case MainActivity.EXERCISE_YOGA:
                return MainActivity.EXERCISE_CARDIO;
            case MainActivity.EXERCISE_CARDIO:
                return MainActivity.EXERCISE_WEIGHTS;
            default:
                System.out.println("ERROR-> DetailActivity.getNextBackgroung() --> Unable to resolve current exercise background...");
                return MainActivity.EXERCISE_WEIGHTS;
        }
    }

    /**
     * Sets the percentage of exercise completion based on how many checkboxes are ticked in each category
     * @param view An optional parameter too allow calling from onClick listener when checkboxes are ticked
     */
    public void setExerciseCompletion(@Nullable View view) {
        int weightChecked = 0;
        int yogaChecked = 0;
        int cardioChecked = 0;

        for (int i = 0; i < weightLinearLayout.getChildCount(); i++) {
            if (weightLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;

//            If this class check doesn't work, I can use a try block to catch when the cast fails
            CheckBox child = (CheckBox) weightLinearLayout.getChildAt(i);
            if (child.isChecked())
                weightChecked += 1;
        }
        for (int i = 0; i < yogaLinearLayout.getChildCount(); i++) {
            if (yogaLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;

            CheckBox child = (CheckBox) yogaLinearLayout.getChildAt(i);
            if (child.isChecked())
                yogaChecked += 1;
        }
        for (int i = 0; i < cardioLinearLayout.getChildCount(); i++) {
            if (cardioLinearLayout.getChildAt(i).getClass() != CheckBox.class)
                continue;

            CheckBox child = (CheckBox) cardioLinearLayout.getChildAt(i);
            if (child.isChecked())
                cardioChecked += 1;
        }

        MainActivity.WeightCompletion = (int) (((float)weightChecked / (float)NUM_CHECKBOX) * 100f);
        MainActivity.YogaCompletion = (int) (((float)yogaChecked / (float)NUM_CHECKBOX) * 100f);
        MainActivity.CardioCompletion = (int) (((float)cardioChecked / (float)NUM_CHECKBOX) * 100f);
        MainActivity.savePrefValues();
    }
    //endregion

    //region Button Listeners
    public void onClickBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickNext(View view) {
        loadBackground(CurrentExerciseBG = getNextBackground(CurrentExerciseBG));
    }

    public void onClickMenu(View view) {

    }

    public void onClickUp(View view) {

    }
    //endregion
}
