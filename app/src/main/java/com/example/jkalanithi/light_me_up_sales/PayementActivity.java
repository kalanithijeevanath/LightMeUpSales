package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Jkalanithi on 23/08/2017.
 */

public class PayementActivity extends Activity {
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tv1 = (TextView) findViewById(R.id.tv1);

    }
}
