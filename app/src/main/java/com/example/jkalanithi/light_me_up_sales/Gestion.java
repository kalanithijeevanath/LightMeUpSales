package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jkalanithi on 14/08/2017.
 */

public class Gestion extends Activity {

    Button gestion_stock;
    Button gestion_produit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        gestion_stock = (Button) findViewById(R.id.stock);
        gestion_produit = (Button) findViewById(R.id.gestion);

        gestion_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gestion.this,GestionStock.class));
            }
        });

        gestion_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gestion.this,GestionProduit.class));
            }
        });

    }
}
