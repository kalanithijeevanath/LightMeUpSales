package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle("WELCOME");
        alertDialog.setMessage("Welcome to LightMeUp sales App");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication) getApplication()).setSomeVariable("admin");
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication) getApplication()).setSomeVariable("client");
                startActivity(new Intent(HomeActivity.this, ListViewActivity.class));
            }
        });

    }


}
