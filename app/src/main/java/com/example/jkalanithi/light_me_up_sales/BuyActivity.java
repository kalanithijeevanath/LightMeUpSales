package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkalanithi on 22/08/2017.
 */

public class BuyActivity extends Activity{
    List<Product> products;
    private DataBaseProduct dataBaseProduct;
    Context context;
    Button button;
    ListView listView;
    List<Product> selectedProducts = new ArrayList<Product>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_cus);
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.buy);
        context = this.getApplicationContext();
        dataBaseProduct = DataBaseProduct.getInstance(context);

        products = dataBaseProduct.queryDB();
        afficherListeProducts();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity.this,PayementActivity.class);
                startActivity(intent);
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
        ProductAdapterCus adapter = new ProductAdapterCus(BuyActivity.this,products);
        listView.setAdapter(adapter);
    }
    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());
        Product p = (Product) listView.getItemAtPosition(position);

        if(cb.isChecked()){
            Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            selectedProducts.add(p);
        }
        else{
            selectedProducts.remove(p);
        }

    }

}

