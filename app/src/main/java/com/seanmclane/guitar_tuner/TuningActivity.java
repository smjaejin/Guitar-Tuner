package com.seanmclane.guitar_tuner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TuningActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GuitarTuningAdapter adapter;
    private List<GuitarTuning>tunings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuning);

        recyclerView= findViewById(R.id.recyclerView);

        tunings = new ArrayList<>();

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GuitarTuningAdapter(this,tunings);
        recyclerView.setAdapter(adapter);
        addTunings();

        adapter.notifyDataSetChanged();




        Log.d("LOOK AT ME", "addTunings: "+tunings.get(1).getName());
        Log.d("LOOK AT ME", "addTunings: "+tunings.size());

    }

    private void addTunings() {
        tunings.add(new GuitarTuning());//standard
        GuitarTuning dropD = new GuitarTuning();
        dropD.setName("Drop D");
        dropD.setS6Name("D");
        dropD.setS6Midi(38);
        tunings.add(dropD);//drop d
        adapter.notifyDataSetChanged();


    }
}
