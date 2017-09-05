package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.Data_Base.DBActivity;
import com.example.jkalanithi.light_me_up_sales.db_helper.MyDBOpenHelper;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    Button b1,b2;
    EditText ed1,ed2;
    TextView sup;
    SQLiteDatabase db;

    TextView tx1;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDB();
        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);

        b2 = (Button) findViewById(R.id.button2);
        tx1 = (TextView) findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        sup = (TextView) findViewById(R.id.sup);
        ((MyApplication)this.getApplication()).setSomeVariable("admin");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login_get = getLogin(ed1.getText().toString());
                String pass_get= getPass(ed1.getText().toString());
                if (ed1.getText().toString().equals(login_get) &&
                        ed2.getText().toString().equals(pass_get)) {
                    db.close();
                    startActivity(new Intent(MainActivity.this, GestionProduit.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                        db.close();
                    }
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                db.close();
            }
        });

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DBActivity.class));
            }
        });
    }



    private void initDB() {
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(getApplicationContext());

        try {
            db = dbHelper.getReadableDatabase(); // Database en lecture seule
        } catch (SQLiteException e) {
            db = openOrCreateDatabase(MyDBOpenHelper.DB_NAME, MODE_PRIVATE, null);
        }
    }
    private String getLogin(String login_put) {
        StringBuilder data = new StringBuilder();

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(MyDBOpenHelper.MY_TABLE_NAME, new String[]{MyDBOpenHelper.FIELD1},MyDBOpenHelper.FIELD1 + " = ? ", new String[]{String.valueOf(login_put)},null,null,null);
            while (cursor.moveToNext()) {
                String login_get = cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD1));
                Log.v(TAG, "login: " + login_get);
                data.append(login_get);
            }
            cursor.close();
        }
        return data.toString();

    }


    private String getPass(String login_put) {
        StringBuilder data = new StringBuilder();

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(MyDBOpenHelper.MY_TABLE_NAME, new String[]{MyDBOpenHelper.FIELD2},MyDBOpenHelper.FIELD1 + " = ? ", new String[]{String.valueOf(login_put)},null,null,null);
            while (cursor.moveToNext()) {
                String pass_get = cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD2));
                Log.v(TAG, "pass: " + pass_get);
                data.append(pass_get);
            }
            cursor.close();
        }

        return data.toString();
    }


}

