package com.sleepless_entertainment.drowsy.faborflab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickNext(View view) {

    }

    public void onClickMenu(View view) {

    }

    public void onClickUp(View view) {

    }
}
