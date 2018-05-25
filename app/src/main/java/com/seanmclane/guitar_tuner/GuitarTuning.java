package com.seanmclane.guitar_tuner;

public class GuitarTuning {
    private String name;
    private String s1Name,s2Name,s3Name,s4Name,s5Name,s6Name;
    private int s1Midi,s2Midi,s3Midi,s4Midi,s5Midi,s6Midi;

    //actual way to play notes

    public GuitarTuning(){
        name="Standard Tuning";
        s6Name="E";
        s5Name="A";
        s4Name="D";
        s3Name="G";
        s2Name="B";
        s1Name="E";
        s6Midi=40;
        s6Midi=45;
        s4Midi=50;
        s3Midi=55;
        s2Midi=59;
        s1Midi=64;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getS1Name() {
        return s1Name;
    }
    public void setS1Name(String s1Name) {
        this.s1Name = s1Name;
    }

    public String getS2Name() {
        return s2Name;
    }
    public void setS2Name(String s2Name) {
        this.s2Name = s2Name;
    }

    public String getS3Name() {
        return s3Name;
    }
    public void setS3Name(String s3Name) {
        this.s3Name = s3Name;
    }

    public String getS4Name() {
        return s4Name;
    }
    public void setS4Name(String s4Name) {
        this.s4Name = s4Name;
    }

    public String getS5Name() {
        return s5Name;
    }
    public void setS5Name(String s5Name) {
        this.s5Name = s5Name;
    }

    public String getS6Name() {
        return s6Name;
    }
    public void setS6Name(String s6Name) {
        this.s6Name = s6Name;
    }


    public int getS1Midi() {
        return s1Midi;
    }
    public void setS1Midi(int s1Midi) {
        this.s1Midi = s1Midi;
    }

    public int getS2Midi() {
        return s2Midi;
    }
    public void setS2Midi(int s2Midi) {
        this.s2Midi = s2Midi;
    }

    public int getS3Midi() {
        return s3Midi;
    }
    public void setS3Midi(int s3Midi) {
        this.s3Midi = s3Midi;
    }

    public int getS4Midi() {
        return s4Midi;
    }
    public void setS4Midi(int s4Midi) {
        this.s4Midi = s4Midi;
    }

    public int getS5Midi() {
        return s5Midi;
    }
    public void setS5Midi(int s5Midi) {
        this.s5Midi = s5Midi;
    }

    public int getS6Midi() {
        return s6Midi;
    }
    public void setS6Midi(int s6Midi) {
        this.s6Midi = s6Midi;
    }


}
