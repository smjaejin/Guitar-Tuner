package com.seanmclane.guitar_tuner;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import be.tarsos.dsp.util.PitchConverter;

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
    Intent intent = getIntent();
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

    private void playSound(int midi) {
        // AudioTrack definition
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);

        double frequency = PitchConverter.midiKeyToHertz(midi);

        AudioTrack mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);

        // Sine wave
        double[] mSound = new double[4410];
        short[] mBuffer = new short[44100];
        for (int i = 0; i < mSound.length; i++) {
            mSound[i] = Math.sin((2.0*Math.PI * i/(44100/frequency)));
            mBuffer[i] = (short) (mSound[i]*Short.MAX_VALUE);
        }

        mAudioTrack.play();

        mAudioTrack.write(mBuffer, 0, mSound.length);
        mAudioTrack.stop();
        mAudioTrack.release();
    }
}
