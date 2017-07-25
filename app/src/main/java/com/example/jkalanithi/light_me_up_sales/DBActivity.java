package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.db.MyDBOpenHelper;

/**
 * Created by Jkalanithi on 24/07/2017.
 */

public class DBActivity extends Activity {

    private static final String TAG = "DBActivity";

    public static final String NL = System.getProperty("line.separator");

    SQLiteDatabase db;
    Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        refreshButton = (Button) findViewById(R.id.refresh);

        initDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    public void add(View v) {
        EditText login = (EditText) findViewById(R.id.login);
        EditText pass = (EditText) findViewById(R.id.pass);
        boolean hasChanged = insertDB(login.getText().toString(),pass.getText().toString());
        setChangeData(hasChanged);
        login.setText(null);
    }
/*
    public void modify(View v) {


        EditText idText = (EditText) findViewById(R.id.idText);
        EditText nameText = (EditText) findViewById(R.id.nameText);

        try {
            long id = Long.parseLong(idText.getText().toString());
            boolean hasChanged = updateDB(id, nameText.getText().toString());
            setChangeData(hasChanged);
        }
        catch (NumberFormatException e) {
        }
        idText.setText(null);
        nameText.setText(null);
    }

    public void delete(View v) {
        EditText idDelete = (EditText) findViewById(R.id.idDelete);
        try {
            long id = Long.parseLong(idDelete.getText().toString());
            boolean hasChanged = deleteDB(id);
            setChangeData(hasChanged);
        }
        catch (NumberFormatException e) {
        }
        idDelete.setText(null);
    }
*/
    public void displayData(View v) {
        setChangeData(false);

        TextView dataText = (TextView) findViewById(R.id.dataText);
        dataText.setText(queryDB());
    }

    private void setChangeData(boolean change) {
        String label = refreshButton.getText().toString();
        if (change && !label.startsWith("*")) {
            refreshButton.setText("*"+label);
        }
        else if (!change && label.startsWith("*")) {
            refreshButton.setText(label.substring(1));
        }
    }


    private void initDB() {
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(getApplicationContext());

        try {
            db = dbHelper.getReadableDatabase(); // Database en lecture seule
//			db = dbHelper.getWritableDatabase(); // Database en lecture/écriture
        }
        catch (SQLiteException e) {
//			Ne pas utiliser de chemin absolu !!!
//			String dbFullFilename = getFilesDir().getAbsolutePath() + File.pathSeparator + MyDBOpenHelper.DB_FILENAME;
//			db = SQLiteDatabase.openOrCreateDatabase(dbFullFilename, null);
            db = openOrCreateDatabase(MyDBOpenHelper.DB_NAME, MODE_PRIVATE, null);
        }
    }

    private boolean insertDB(String login,String pass) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            ContentValues values = new ContentValues();
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



}
