package com.seanmclane.guitar_tuner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.util.PitchConverter;

public class MainActivity extends AppCompatActivity {

    public final static String[] NOTE_NAMES = new String[] { "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B" };
    public final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final double pitchInHz = result.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d(TAG, "run: "+PitchConverter.hertzToMidiKey(pitchInHz));
                        if (pitchInHz > 0.0) {
                            //Log.d(TAG, "run: "+pitchInHz);
                            animateIndicator(pitchInHz, rangeOfNote(pitchInHz));
                        }
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher,"Audio Dispatcher").start();

    }

    private int rangeOfNote(double givenHz) {
        int rangeScores[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
        int midiCurrent = PitchConverter.hertzToMidiKey(givenHz);
        int midiBelow = midiCurrent - 1;
        int midiAbove = midiCurrent + 1;
        int index;
        double rangeOfBelow = PitchConverter.midiKeyToHertz(midiCurrent) - PitchConverter.midiKeyToHertz(midiBelow);
        double rangeOfAbove = PitchConverter.midiKeyToHertz(midiAbove) - PitchConverter.midiKeyToHertz(midiCurrent);
        double difference = PitchConverter.midiKeyToHertz(midiCurrent)-givenHz;
        if (difference>=0) {
            double preIndex = difference/rangeOfAbove;
            index = (int) Math.round(10+10*preIndex);
        }
        else {
            double preIndex = difference/rangeOfBelow;
            index = (int) Math.round(10 - preIndex*10);
        }
        Log.d(TAG, "rangeOfNote: "+rangeScores[index]);
        return rangeScores[index];
    }

    private void animateIndicator(double pitchInHz, int range){
        TextView text = (TextView) findViewById(R.id.textViewNote);
        text.setText("" + getNoteStringFromHzValue(pitchInHz));
    }

    private String getNoteStringFromHzValue(Double pitchInHz) {
        if (PitchConverter.hertzToMidiKey(pitchInHz)==0)
            return "Play a note";
        return  NOTE_NAMES[PitchConverter.hertzToMidiKey(pitchInHz)%12];
    }


}
