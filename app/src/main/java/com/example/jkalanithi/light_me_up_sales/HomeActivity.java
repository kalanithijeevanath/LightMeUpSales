package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jkalanithi on 31/07/2017.
 */

public class HomeActivity extends Activity {

    Button commercial;
    Button client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        commercial = (Button) findViewById(R.id.commercial);
        client = (Button) findViewById(R.id.client);
        ((MyApplication)this.getApplication()).setSomeVariable("admin");

        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,BuyActivity.class));
            }
        });

    }




}
