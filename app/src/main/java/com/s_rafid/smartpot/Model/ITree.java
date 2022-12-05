package com.s_rafid.smartpot.Model;

public interface ITree {
    String getName();
    float getSoilMoisture();
    float getTemp();
    float getHum();
    float getPumpStatus();
    boolean updatePump(int val);
    String getAuth();
    int isValid();
    int getMood(int t,int h,int m);
}
