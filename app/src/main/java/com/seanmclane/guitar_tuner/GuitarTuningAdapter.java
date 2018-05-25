package com.seanmclane.guitar_tuner;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.tarsos.dsp.util.PitchConverter;

public class GuitarTuningAdapter extends RecyclerView.Adapter<GuitarTuningAdapter.MyViewHolder> {
    private Context context;
    private List<GuitarTuning> tunings;
    public GuitarTuningAdapter(Context context, List<GuitarTuning> tunings) {
        this.context = context;
        this.tunings = tunings;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guitar_tuning_item,parent,false);
        MyViewHolder tvh = new MyViewHolder(itemView);
        return tvh;
    }


    //assigns approptiate info from the word object in each widget in the layout
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GuitarTuning tuning = tunings.get(position);
        holder.tuningNameText.setText(tuning.getName());
        holder.sixStringButton.setText(tuning.getS6Name());
        holder.fiveStringButton.setText(tuning.getS5Name());
        holder.fourStringButton.setText(tuning.getS4Name());
        holder.threeStringButton.setText(tuning.getS3Name());
        holder.twoStringButton.setText(tuning.getS2Name());
        holder.oneStringButton.setText(tuning.getS1Name());

    }

    @Override
    public int getItemCount() {return tunings.size();}



    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView sixStringButton,fiveStringButton,fourStringButton,threeStringButton,twoStringButton,oneStringButton,tuningNameText;

        public MyViewHolder(View itemView) {
            super(itemView);
            tuningNameText = itemView.findViewById(R.id.TuningName);
            sixStringButton = itemView.findViewById(R.id.sixString);
            sixStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS6Midi());
                }
            });
            fiveStringButton = itemView.findViewById(R.id.fiveString);
            fiveStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS5Midi());
                }
            });
            fourStringButton = itemView.findViewById(R.id.fourString);
            fourStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS4Midi());
                }
            });
            threeStringButton = itemView.findViewById(R.id.threeString);
            threeStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS3Midi());
                }
            });
            twoStringButton = itemView.findViewById(R.id.twoString);
            twoStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS2Midi());
                }
            });
            oneStringButton = itemView.findViewById(R.id.oneString);
            oneStringButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    playSound(tunings.get(getAdapterPosition()).getS3Midi());
                }
            });
        }
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
        double[] mSound = new double[44100];
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
