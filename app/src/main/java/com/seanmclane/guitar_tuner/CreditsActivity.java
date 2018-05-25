package com.seanmclane.guitar_tuner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {
    public boolean screen;
    @Override
    protected void onResume() {
        super.onResume();
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        boolean currentScreen =  sharedPref.getBoolean(getString(R.string.background_style), true);
        if(currentScreen!=screen){
            recreate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.credits);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        screen = sharedPref.getBoolean(getString(R.string.background_style), true);
//use maylis whetsels shared preferences tutorial
        if(!screen)
        {setContentView(R.layout.credits);}
        else {
            setContentView(R.layout.credits_dark);
        }
        Intent j = getIntent();

    }
}
