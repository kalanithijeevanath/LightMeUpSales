package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Jkalanithi on 18/08/2017.
 */

public class SelectionActivity extends Activity {
    private DataBaseProduct dataBaseProduct;
    Context context;
    Button delete_product;
    Button modify_product;
    Button add_stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        context = this.getApplicationContext();

        dataBaseProduct = DataBaseProduct.getInstance(context);
        delete_product = (Button)findViewById(R.id.delete);
        modify_product = (Button) findViewById(R.id.edit);
        add_stock = (Button) findViewById(R.id.stock);


        delete_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String ref = extras.getString("ref");

                boolean hasChanged = dataBaseProduct.deleteDB(ref);
                Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_SHORT).show();

            }
        });

        modify_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String ref = extras.getString("ref");
                Intent appInfo = new Intent(SelectionActivity.this, ModifyProductActivity.class);
                appInfo.putExtra("reference", ref);
                startActivity(appInfo);
            }
        });

        add_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String ref = extras.getString("ref");
                Intent appInfo = new Intent(SelectionActivity.this, AddStockActivity.class);
                appInfo.putExtra("reference", ref);
                startActivity(appInfo);
            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
