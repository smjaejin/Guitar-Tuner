package com.seanmclane.guitar_tuner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
    public final static int REQUEST_AUDIO_ACCSESS=0;
    private SeekBar seekBar;
    private boolean screen;

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
        boolean currentScreen1 = sharedPref.getBoolean("tuningPreference", true);
        //if(currentScreen1)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        screen = sharedPref.getBoolean(getString(R.string.background_style), true);
        //screen1 =sharedPref.getBoolean("tuningPreference", true);
//use maylis whetsels shared preferences tutorial
       if(!screen)
       {setContentView(R.layout.activity_main);}
       else setContentView(R.layout.activity_main_dark);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_AUDIO_ACCSESS);
        }
        else{
            startRecording();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                openSettings();
                }
        switch (item.getItemId()) {
            case R.id.credits:
                openCredits();
        }
        return true;

    }


    private void openCredits() {
    Intent j = new Intent(MainActivity.this,CreditsActivity.class);
    startActivity(j);
    }

    private void openSettings() {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }


    private void startRecording() {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

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
        new Thread(dispatcher, "Audio Dispatcher").start();
    }

    private void requestAudio() {
    }

    /**
     * @param givenHz the Hz value from a mic input
     *
     * @return a double ranging from -10.0 to 10.0
     */

    private double rangeOfNote(double givenHz) {
        //This rounds the Hz value to its closest midi key
        int midiCurrent = PitchConverter.hertzToMidiKey(givenHz);
        int midiBelow = midiCurrent - 1;
        int midiAbove = midiCurrent + 1;
        double index;
        //finds difference of Hz value between the midi keys above and below the current one
        double rangeOfBelow = (PitchConverter.midiKeyToHertz(midiCurrent) - PitchConverter.midiKeyToHertz(midiBelow))/2;
        double rangeOfAbove = (PitchConverter.midiKeyToHertz(midiAbove) - PitchConverter.midiKeyToHertz(midiCurrent))/2;
        //finds the difference between the rounded Hz value and the actual Hz value
        double difference = PitchConverter.midiKeyToHertz(midiCurrent)-givenHz;
        if (difference>=0) {
            index = 10*(difference/rangeOfAbove);
        }
        else {
            index = 10*(difference/rangeOfBelow);
        }
        //Log.d(TAG, "rangeOfNote: "+index);
        //if (index<-10 || index>10)
            //Log.d(TAG, "OUTSIDE OF RANGE Hz Value: "+givenHz+" NOTE VALUE: "+NOTE_NAMES[midiCurrent%12]);
        return index;
    }

    private void playSound(double frequency) {
        // AudioTrack definition
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);

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

    private void animateIndicator(double pitchInHz, double range){
        TextView text = (TextView) findViewById(R.id.textViewNote);
        text.setText("" + getNoteStringFromHzValue(pitchInHz));
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(2000);
        //seekBar.setProgress( (11*100) , true);
        if((rangeOfNote(pitchInHz)+10)<0){
            seekBar.setProgress(0);
        }
        else{
        seekBar.setProgress((int) ((rangeOfNote(pitchInHz)+10)*100),true);
        }
        seekBar.setProgress(0);


    }

    private String getNoteStringFromHzValue(Double pitchInHz) {
        if (PitchConverter.hertzToMidiKey(pitchInHz)==0)
            return "Play a note";
        return  NOTE_NAMES[PitchConverter.hertzToMidiKey(pitchInHz)%12];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();

        } else {

            Toast.makeText(this, "Access to the microphone is required for this app to run properly", Toast.LENGTH_SHORT).show();
        }
        return;
    }

}
