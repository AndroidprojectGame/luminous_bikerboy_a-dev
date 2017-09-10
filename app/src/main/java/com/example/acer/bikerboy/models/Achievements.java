package com.example.acer.bikerboy.models;

/**
 * Created by acer on 3/28/2016.
 */
public class Achievements {

    public String CurrentMonthTargerFan, CurrentMonthTargerWire, CurrentMonthTargerLighting, CurrentMonthAchievementFan, CurrentMonthAchievementWire, CurrentMonthAchievementLighting, CurrentMonthSuppliedWire, CurrentMonthSuppliedFan, CurrentMonthSuppliedLighting;

    public Achievements(String tFan, String tWire, String tLight, String aFan, String aWire, String aLight, String sWire, String sFan, String sLight) {
        // super();
        CurrentMonthTargerFan = tFan;
        CurrentMonthTargerWire = tWire;
        CurrentMonthTargerLighting = tLight;
        CurrentMonthAchievementFan = aFan;
        CurrentMonthAchievementWire = aWire;
        CurrentMonthAchievementLighting = aLight;
        CurrentMonthSuppliedWire = sWire;
        CurrentMonthSuppliedFan = sFan;
        CurrentMonthSuppliedLighting = sLight;

        //this.dis_name = dis_name;
    }
}
