package com.seanmclane.guitar_tuner;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import static com.seanmclane.guitar_tuner.MainActivity.TAG;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {
    private Context context;
    private List<Settings> settings;
    public SettingsAdapter(Context context, List<Settings> settings){
        this.context = context;
        this.settings = settings;
    }

    public static final int BACKGROUND_COLOR = 0;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
              Settings setting = settings.get(position);
              holder.name.setText(setting.getName());




        // holder.switchDefault.setChecked(setting.getSwitch());
        holder.switchDefault.setChecked(setting.getSwitch());
SharedPreferences sharedPref =context.getSharedPreferences("SharedPreferenceFileKey", Context.MODE_PRIVATE);
boolean currentValue = sharedPref.getBoolean("backgroundStyle", true);
holder.switchDefault.setChecked(currentValue);
        holder.switchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch(position) {
                    case BACKGROUND_COLOR:
                        if(isChecked)
                        {SharedPreferences sharedPref = context.getSharedPreferences(
                                context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            editor.putBoolean("backgroundStyle", true);
                            editor.commit();

                            Log.d(TAG, "onCheckedChanged: true");

                        }
                        else {
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();

                                editor.putBoolean("backgroundStyle", false);
                                editor.commit();
                                Log.d(TAG, "onCheckedChanged: false");
                            }
                        break;


                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private Switch switchDefault;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView2);
            switchDefault = itemView.findViewById(R.id.switch1);



        }
    }

}
