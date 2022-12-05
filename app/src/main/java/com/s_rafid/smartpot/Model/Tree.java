package com.s_rafid.smartpot.Model;

import android.text.TextUtils;
import android.util.Log;

public class Tree implements ITree{
    String name,auth;
    public Tree(String name, String auth) {
        this.name = name;
        this.auth = auth;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getSoilMoisture() {
        return 0;
    }

    @Override
    public float getTemp() {
        return 0;
    }

    @Override
    public float getHum() {
        return 0;
    }

    @Override
    public float getPumpStatus() {
        return 0;
    }

    @Override
    public boolean updatePump(int val) {
        return false;
    }

    @Override
    public String getAuth() {
        return auth;
    }

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(getName()) || TextUtils.isEmpty(getAuth()))
            return  0;
        else
            return 1;
    }

    @Override
    public int getMood(int t, int h, int m) {
        /*
        0 ok
        1 h temp
        2 l temp
        3 l hum
        4 thirsty
         */
        if(t>40){
            return 1;
        }
        else if(t<18){
            return 2;
        }
        else if(h<40){
            return 3;
        }
        else if(m<50){
            return 4;
        }
        else{
            return 0;
        }
    }
}
