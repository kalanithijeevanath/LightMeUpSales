package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jkalanithi on 04/08/2017.
 */

public class ListViewActivity extends ListActivity {

ArrayList<Product> products;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myintent = getIntent();

        if (null != myintent.getExtras()) {

            products = ProductDao.dataBaseProduct.queryDB();
            if(products != null){
                ProductAdapter productAdapter = new ProductAdapter(this,R.layout.ligne,products);
                setListAdapter(productAdapter);
            }
        }
    }
}
