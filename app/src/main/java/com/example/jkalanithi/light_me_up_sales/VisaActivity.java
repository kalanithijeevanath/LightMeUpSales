package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.example.jkalanithi.light_me_up_sales.Model.Product;

/**
 * Created by Jkalanithi on 05/09/2017.
 */

public class VisaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa);

        Bundle extras = getIntent().getExtras();
        final String price = extras.getString("prix");

        CardForm cardForm = (CardForm) findViewById(R.id.cardform);
        TextView textView = (TextView) findViewById(R.id.payment_amount);
        Button btnPay = (Button) findViewById(R.id.btn_pay);

        textView.setText(price + " €");
        btnPay.setText(String.format("Payed %s", textView.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(getApplicationContext(), "Payed with VisaCard", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}
