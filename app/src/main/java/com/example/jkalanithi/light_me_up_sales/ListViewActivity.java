package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.Adapter.ProductAdapter;
import com.example.jkalanithi.light_me_up_sales.Data_Base.DataBaseProduct;
import com.example.jkalanithi.light_me_up_sales.Model.Product;
import com.example.jkalanithi.light_me_up_sales.Model.ProductCady;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkalanithi on 04/08/2017.
 */

public class ListViewActivity extends Activity {
    List<Product> products;
    ArrayList<String> refs = new ArrayList<String>();
    ArrayList<String> product_num = new ArrayList<String>();
    private DataBaseProduct dataBaseProduct;
    Context context;
    ListView listView;
    Button pay;
    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = (ListView) findViewById(R.id.listView);
        pay = (Button) findViewById(R.id.pay);
        context = this.getApplicationContext();
        dataBaseProduct = DataBaseProduct.getInstance(context);

        s = ((MyApplication) getApplication()).getSomeVariable();
        products = dataBaseProduct.queryDB();
        afficherListeProducts();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product p = (Product) parent.getItemAtPosition(position);


                Intent appInfo = null;
                if (s.equals("admin") == true) {
                    appInfo = new Intent(ListViewActivity.this, SelectionActivity.class);
                }
                if (s.equals("client") == true) {
                    appInfo = new Intent(ListViewActivity.this, ProductCady.class);
                    pay.setEnabled(true);
                    pay.setVisibility(View.VISIBLE);
                }
                appInfo.putExtra("ref", p.getRef());
                //startActivity(appInfo);
                startActivityForResult(appInfo, 1);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produit = new Intent(ListViewActivity.this, PayementActivity.class);
                produit.putStringArrayListExtra("produit", refs);
                produit.putStringArrayListExtra("nb", product_num);
                startActivity(produit);
                refs.clear();
                product_num.clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data.hasExtra("ref") && data.hasExtra("nb")) {
                Toast.makeText(this, data.getExtras().getString("ref"),
                        Toast.LENGTH_SHORT).show();
                refs.add(data.getExtras().getString("ref"));
                product_num.add(data.getExtras().getString("nb"));
            }
        }
    }

    private List<Product> generateData() {

        List<Product> items = new ArrayList<Product>();
        String[] data = new String[products.size()];

        for (int i = 0; i < products.size(); i++) {
            data[i] = products.get(i).getProduct_name();
            if (s.equals("client") == true) {
                if (products.get(i).getProduct_stock() > 0) {
                    items.add(new Product(products.get(i).getProduct_name(), products.get(i).getRef(), products.get(i).getProduct_price_ttc(), products.get(i).getPath_image(), products.get(i).getProduct_stock()));
                }
            }
            if (s.equals("admin") == true) {
                items.add(new Product(products.get(i).getProduct_name(), products.get(i).getRef(), products.get(i).getProduct_price_ttc(), products.get(i).getPath_image(), products.get(i).getProduct_stock()));
                if (products.get(i).getProduct_stock() == 0) {

                }
            }
        }
        return items;
    }

    private void afficherListeProducts() {
        List<Product> products = generateData();

        ProductAdapter adapter = new ProductAdapter(ListViewActivity.this, products);
        listView.setAdapter(adapter);
    }

}
