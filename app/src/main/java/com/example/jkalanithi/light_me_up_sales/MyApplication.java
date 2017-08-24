package com.example.jkalanithi.light_me_up_sales;

import android.app.Application;

/**
 * Created by Jkalanithi on 24/08/2017.
 */

public class MyApplication extends Application {

    private String admin;

    public String getSomeVariable() {
        return admin;
    }

    public void setSomeVariable(String admin) {
        this.admin = admin;
    }
}
