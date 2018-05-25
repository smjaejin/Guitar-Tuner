package com.seanmclane.guitar_tuner;

import android.widget.Switch;
import android.widget.TextView;

public class Settings {

    private boolean switchDefault;
    private String name;

    public Settings(boolean bool, String name1){
        switchDefault = bool;
        name= name1;
    }

    public String getName() {
        return name;
    }
    public void setName(String name1) {
        name=name1;
    }

    public boolean getSwitch(){
        return switchDefault;
    }
    public void setSwitch(boolean switchDefault1){
        switchDefault=switchDefault1;
    }
}
