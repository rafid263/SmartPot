package com.s_rafid.smartpot.Model;

import android.text.TextUtils;

public class TreeModel implements ITreeModel {
    String name,auth,email;
    public TreeModel(String name, String auth,String email) {
        this.name = name;
        this.auth = auth;
        this.email = email;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAuth() {
        return auth;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(getName()) || TextUtils.isEmpty(getAuth()))
            return  0;
        else
            return 1;
    }
}
