package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.db_helper.MyDBOpenHelper;

/**
 * Created by Jkalanithi on 24/07/2017.
 */

public class DBActivity extends Activity {

    private static final String TAG = "DBActivity";

    public static final String NL = System.getProperty("line.separator");

    TextView back;
    SQLiteDatabase db;
    EditText login,pass,mail,phone;
    String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        back= (TextView) findViewById(R.id.lin);
        //refreshButton = (Button) findViewById(R.id.refresh);

        initDB();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    public void add(View v) {
        boolean state;
        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.pass);
        mail = (EditText) findViewById(R.id.mail);
        phone = (EditText) findViewById(R.id.mobphone);

        phone_number = phone.getText().toString();

        if(TextUtils.isEmpty(mail.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()) || TextUtils.isEmpty(login.getText().toString()) ) {
            if (TextUtils.isEmpty(mail.getText().toString())){
                mail.setError("Mail cannot be empty");
            }
            if (TextUtils.isEmpty(login.getText().toString())){
                login.setError("Login cannot be empty");
            }
            if(TextUtils.isEmpty(pass.getText().toString())){
                pass.setError("Password cannot be empty");
            }
            if(TextUtils.isEmpty(phone.getText().toString())){
                phone_number = "";
            }
            return;
        }
        state = CheckIsExist(login.getText().toString());
        if(state == true) {
            login.setError("Login already exist");
            return;
        }
        boolean hasChanged = insertDB(mail.getText().toString(),phone_number,login.getText().toString(),pass.getText().toString());
        login.setText(null);
    }
    private void initDB() {
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(getApplicationContext());

        try {
            db = dbHelper.getReadableDatabase(); // Database en lecture seule
        }
        catch (SQLiteException e) {
            db = openOrCreateDatabase(MyDBOpenHelper.DB_NAME, MODE_PRIVATE, null);
        }
    }

    private boolean insertDB(String mail,String phone,String login,String pass) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            ContentValues values = new ContentValues();
            values.put(MyDBOpenHelper.FIELD3, mail);
            values.put(MyDBOpenHelper.FIELD4, phone);
            values.put(MyDBOpenHelper.FIELD1, login);
            values.put(MyDBOpenHelper.FIELD2, pass);
            long id = db.insert(MyDBOpenHelper.MY_TABLE_NAME, null, values);
            Toast.makeText(this, "insertDB: " + id, Toast.LENGTH_SHORT).show();
            return (id > -1);
        }
        return false;
    }

    private boolean updateDB(long id, String newValue) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            ContentValues values = new ContentValues();
            values.put(MyDBOpenHelper.FIELD1, newValue);
            int countRows = db.update(MyDBOpenHelper.MY_TABLE_NAME, values, "_id = ?", new String[] { String.valueOf(id) });
            Toast.makeText(this, "updateDB: " + countRows, Toast.LENGTH_SHORT).show();
            return (countRows > 0);
        }
        return false;
    }

    private boolean deleteDB(long id) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            int countRows = db.delete(MyDBOpenHelper.MY_TABLE_NAME, "_id = ?", new String[] { String.valueOf(id) });
            Toast.makeText(this, "deleteDB: " + countRows, Toast.LENGTH_SHORT).show();
            return (countRows > 0);
        }
        return false;
    }

    private String queryDB() {
        StringBuilder data = new StringBuilder();

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(MyDBOpenHelper.MY_TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String login = cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD1));
                String pass = cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD2));
                data.append(id).append(";").append(login).append(";").append(pass).append(NL);
            }
            cursor.close();
        }
        return data.toString();
    }

    public boolean CheckIsExist(String login){
        Cursor  cursor = db.rawQuery("SELECT * FROM " +MyDBOpenHelper.MY_TABLE_NAME+ " WHERE LOGIN=?" ,new String[]{login + ""});
        if (cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }
}
