package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jkalanithi on 14/08/2017.
 */

public class GestionProduit extends Activity {
    Button add_produit;
    Button show_produits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_produit);

        add_produit = (Button) findViewById(R.id.add);
        show_produits = (Button) findViewById(R.id.show);

        add_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionProduit.this,AddProductActivity.class));
            }
        });

        show_produits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionProduit.this,ListViewActivity.class));
            }
        });

    }

}
