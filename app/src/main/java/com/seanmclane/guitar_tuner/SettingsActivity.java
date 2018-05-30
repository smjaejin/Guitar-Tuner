package com.seanmclane.guitar_tuner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Settings> settings;
    private Switch changeBackground;
    private Switch tuneActivity;
    private TextView toolTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        settings = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SettingsAdapter(this, settings);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent i = getIntent();
        Settings backgroundMode = new Settings(false, "Light Mode/Dark Mode" );
        Settings tuning = new Settings(false, "Advanced/Standard");
        changeBackground = (Switch) findViewById(R.id.switch1);
        tuneActivity = (Switch) findViewById(R.id.switch1);
        toolTitle = (TextView) findViewById(R.id.textView2);
        settings.add(backgroundMode);
        settings.add(tuning);

//        changeBackground.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    changeBackGroundToBlack();
//                }
//                else changeBackgroundToWhite();
//            }
//        });

    }

    public void changeBackGroundToBlack() {
        View mainView = findViewById(R.id.settings_screen);
        View root = mainView.getRootView();
        View mainView1 = findViewById(R.id.main_screen);
        View root1 = mainView1.getRootView();
        root1.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

    }
    public void changeBackgroundToWhite(){
        View mainView = findViewById(R.id.settings_screen);
        View root = mainView.getRootView();
        root.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        View mainView1 = findViewById(R.id.main_screen);
        View root1 = mainView1.getRootView();
        root1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

}
