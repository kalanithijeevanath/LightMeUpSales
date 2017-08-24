package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.concat;

/**
 * Created by Jkalanithi on 04/08/2017.
 */

public class ListViewActivity extends Activity {
    ArrayList<Product> products;
    private DataBaseProduct dataBaseProduct;
    Context context;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        context = this.getApplicationContext();
        dataBaseProduct = DataBaseProduct.getInstance(context);

        products = dataBaseProduct.queryDB();
        afficherListeProducts();

        String s = ((MyApplication) this.getApplication()).getSomeVariable();
        //Toast.makeText(getApplicationContext(), "Global Variable : " + s, Toast.LENGTH_SHORT).show();
        Log.d("variable", s);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product p = (Product) parent.getItemAtPosition(position);
                Intent appInfo = new Intent(ListViewActivity.this, SelectionActivity.class);
                appInfo.putExtra("ref",p.getRef());
                startActivity(appInfo);

            }
        });
    }

    private List<Product> generateData(){

        List<Product> items = new ArrayList<Product>();
        String[] data = new String[products.size()];

        for(int i=0;i<products.size();i++) {
            data[i] = products.get(i).getProduct_name();
            items.add(new Product(products.get(i).getProduct_name(), products.get(i).getRef(), products.get(i).getProduct_price_ttc(), products.get(i).getPath_image(),products.get(i).getProduct_stock()));
        }
        return items;
    }
    private void afficherListeProducts(){
        List<Product> products = generateData();

        ProductAdapter adapter = new ProductAdapter(ListViewActivity.this,products);
        listView.setAdapter(adapter);
    }

}
