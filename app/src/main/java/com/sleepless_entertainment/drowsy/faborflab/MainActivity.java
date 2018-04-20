package com.sleepless_entertainment.drowsy.faborflab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //region Button Listeners
    public void onClickWeightlifting(View view) {
        assert view != null;
        System.out.println("Click Weightlifting");
    }

    public void onClickYoga(View view) {
        System.out.println("Click Yoga");
    }

    public void onClickCardio(View view) {
        System.out.println("Click Cardio");
    }
    //endregion
}
