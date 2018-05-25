package com.seanmclane.guitar_tuner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
            fiveStringButton = itemView.findViewById(R.id.fiveString);
            fourStringButton = itemView.findViewById(R.id.fourString);
            threeStringButton = itemView.findViewById(R.id.threeString);
            twoStringButton = itemView.findViewById(R.id.twoString);
            oneStringButton = itemView.findViewById(R.id.oneString);
        }
    }
}
