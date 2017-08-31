package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jkalanithi on 28/08/2017.
 */

public class ProductCady extends Activity {
    int minteger = 1;
    DataBaseProduct dataBaseProduct;
    Context context;
    Integer product_stock;
    TextView textView;
    Button button;
    TextView displayInteger;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cady);
        Bundle extras = getIntent().getExtras();
        final String ref = extras.getString("ref");
        dataBaseProduct = DataBaseProduct.getInstance(context);
        product = dataBaseProduct.getProdect(ref);
        textView = (TextView) findViewById(R.id.product_select_name);
        textView.setText(product.getProduct_name());
        button = (Button) findViewById(R.id.add_to_cady);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent();
                appInfo.putExtra("ref", ref);
                displayInteger = (TextView) findViewById(R.id.integer_number);
                appInfo.putExtra("nb", displayInteger.getText().toString());
                setResult(RESULT_OK, appInfo);
                finish();
            }
        });
    }

    public void increaseInteger(View view) {
        product_stock = product.getProduct_stock();
        if (minteger < product_stock) {
            minteger = minteger + 1;
        }
        display(minteger);
    }

    public void decreaseInteger(View view) {
        product_stock = product.getProduct_stock();
        if (minteger > 1) {
            minteger = minteger - 1;
        }
        display(minteger);
    }

    private void display(int number) {
        displayInteger = (TextView) findViewById(R.id.integer_number);
        displayInteger.setText(String.valueOf(number));
    }
}
