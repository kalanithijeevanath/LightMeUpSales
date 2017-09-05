package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.Adapter.ProductCartAdapter;
import com.example.jkalanithi.light_me_up_sales.Data_Base.DataBaseProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkalanithi on 23/08/2017.
 */

public class PayementActivity extends Activity {
    private DataBaseProduct dataBaseProduct;
    Context context;
    Button paypal, visa, back;
    Integer new_stock;
    Integer nb_choix;
    ArrayList<String> ref;
    ArrayList<String> nb;
    ListView listView;
    TextView textView;
    Double total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paypal = (Button) findViewById(R.id.paypal);
        visa = (Button) findViewById(R.id.visa);
        back = (Button) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.list_item1);
        textView = (TextView) findViewById(R.id.total_price);
        ref = getIntent().getStringArrayListExtra("produit");
        nb = getIntent().getStringArrayListExtra("nb");


        context = this.getApplicationContext();

        dataBaseProduct = DataBaseProduct.getInstance(context);

        afficherListeProducts();

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStock();
                Toast.makeText(getApplicationContext(), "Payed with Paypal", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStock();
                Intent visa = new Intent(PayementActivity.this, VisaActivity.class);
                visa.putExtra("prix",total_price.toString());
                startActivity(visa);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void changeStock (){
        for (int i = 0; i < ref.size(); i++) {

            new_stock = dataBaseProduct.getStock(ref.get(i));
            new_stock = new_stock - (Integer.parseInt(nb.get(i)));
            if (new_stock == 0){
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Produit n'est plus en stock")
                        .setContentText(ref.get(i));
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(001,mBuilder.build());
            }
            dataBaseProduct.updateStock(ref.get(i), new_stock);

        }
    }

    private List<CartProduct> dataCharge()
    {
        total_price = 0.0;
        List<CartProduct> cart = new ArrayList<>();
        for (int i = 0; i < ref.size(); i++) {
            nb_choix =Integer.parseInt(nb.get(i));
            cart.add(new CartProduct(dataBaseProduct.getName(ref.get(i)),dataBaseProduct.getPrice(ref.get(i)),nb_choix));
            total_price = total_price + (dataBaseProduct.getPrice(ref.get(i))*nb_choix);
        }
        return cart;
    }

    private void afficherListeProducts() {
        List<CartProduct> products = dataCharge();
        ProductCartAdapter adapter = new ProductCartAdapter(PayementActivity.this, products);
        textView.setText("TOTAL PRICE : " + total_price.toString() + " €");
        listView.setAdapter(adapter);
    }

}
