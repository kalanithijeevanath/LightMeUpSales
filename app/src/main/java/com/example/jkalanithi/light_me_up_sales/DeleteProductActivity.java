package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jkalanithi on 14/08/2017.
 */

class DeleteProductActivity extends Activity{
    private DataBaseProduct dataBaseProduct;
    Context context;
    Button delete_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        context = this.getApplicationContext();

        dataBaseProduct = DataBaseProduct.getInstance(context);
        delete_product = (Button)findViewById(R.id.delete_product);


        delete_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ref = (EditText) findViewById(R.id.product_ref);
                boolean hasChanged = dataBaseProduct.deleteDB(ref.getText().toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
