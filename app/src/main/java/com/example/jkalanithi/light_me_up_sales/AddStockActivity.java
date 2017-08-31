package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jkalanithi on 22/08/2017.
 */

public class AddStockActivity extends Activity {
    private DataBaseProduct dataBaseProduct;
    Context context;
    Button add_stock;
    EditText stock;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        stock = (EditText) findViewById(R.id.product_stock_added);
        add_stock = (Button) findViewById(R.id.add_stock_product);
        context = this.getApplicationContext();
        dataBaseProduct = DataBaseProduct.getInstance(context);

       add_stock.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String stock1 = stock.getText().toString();
                   if (TextUtils.isEmpty(stock.getText().toString())){
                       stock.setError("Product stock cannot be empty");
                       return;
                   }
               Integer stock_final = Integer.parseInt(stock1);

               Bundle extras = getIntent().getExtras();
               final String ref = extras.getString("reference");
               final Product product = dataBaseProduct.getProdect(ref);
               if(product.getProduct_stock()==0 ||stock_final ==0)
               {
                   stock_final = stock_final + 0;
               }else {
                   stock_final = stock_final + product.getProduct_stock();
               }
               boolean hasChanged = dataBaseProduct.updateStock(ref,stock_final);
               Toast.makeText(getApplicationContext(), "Stock updated", Toast.LENGTH_SHORT).show();
               finish();
           }
       });

    }
}
