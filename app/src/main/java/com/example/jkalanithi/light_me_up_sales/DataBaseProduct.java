package com.example.jkalanithi.light_me_up_sales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.db.MyDBOpenHelper;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class DataBaseProduct {

    private SQLiteDatabase db;
    private Context context;

    private static DataBaseProduct instance = new DataBaseProduct();
    private boolean initialized=false;


    public static DataBaseProduct getInstance(Context context){
        if (! instance.initialized){
            instance.initDB(context);
        }
        return instance;
    }

    DataBaseProduct(){
        super();
    }

   // public  void setContext(Context context) {
   //     this.context = context;
   // }

    // initialisation de la base de donnée
    private void initDB(Context context) {

        MyDBOpenHelper dbHelper = new MyDBOpenHelper(context.getApplicationContext());

        try {
            db = dbHelper.getWritableDatabase(); // Database en lecture/écriture
            initialized=true;
        } catch (SQLiteException e) {
            db = SQLiteDatabase.openOrCreateDatabase(dbHelper.DB_NAME, null);
        }
    }

    //insertion dans une table
    public boolean insertDB(String productName, String ref, Integer productPriceHt, Integer productTva, Integer productPriceTtc, Integer productStock, String productDescription, String pathImageLocal) {
        try {

            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put(MyDBOpenHelper.FIELD6, productName);
                values.put(MyDBOpenHelper.FIELD7, ref);
                values.put(MyDBOpenHelper.FIELD8, productPriceHt);
                values.put(MyDBOpenHelper.FIELD9, productTva);
                values.put(MyDBOpenHelper.FIELD10, productPriceTtc);
                values.put(MyDBOpenHelper.FIELD12, productStock);
                values.put(MyDBOpenHelper.FIELD13, productDescription);
                values.put(MyDBOpenHelper.FIELD5, pathImageLocal);

                long id = db.insertOrThrow(MyDBOpenHelper.MY_TABLE_NAME1, null, values);
                //Toast.makeText(context, "insertDB: " + id, LENGTH_SHORT).show();
                return (id > 0);
            }
        }catch (Exception e){
            Log.d("Exception", "insertDB: "+e);
        }
        return false;
    }

    //mise à jour d'une ligne
    public boolean updateDB(String newProductName, String ref, Integer newProductPriceHt, Integer newProductTva, Integer newProductPriceTtc, Integer newProductStock, String newProductDescription, String newPathImageLocal) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            ContentValues values = new ContentValues();
            values.put(MyDBOpenHelper.FIELD6, newProductName);
            values.put(MyDBOpenHelper.FIELD7, ref);
            values.put(MyDBOpenHelper.FIELD8, newProductPriceHt);
            values.put(MyDBOpenHelper.FIELD9, newProductTva);
            values.put(MyDBOpenHelper.FIELD10, newProductPriceTtc);
            values.put(MyDBOpenHelper.FIELD12, newProductStock);
            values.put(MyDBOpenHelper.FIELD13, newProductDescription);
            values.put(MyDBOpenHelper.FIELD5, newPathImageLocal);
            int countRows = db.update(MyDBOpenHelper.MY_TABLE_NAME1, values, "REF = ?", new String[]{String.valueOf(ref)});
            //Toast.makeText(context, "updateDB: " + countRows, LENGTH_SHORT).show();
            return (countRows > 0);
        }
        return false;
    }
    //suppression d'une ligne
    public boolean deleteDB(String ref) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            int countRows = db.delete(MyDBOpenHelper.MY_TABLE_NAME1, "REF = ?", new String[]{String.valueOf(ref)});
            //Toast.makeText(context, "deleteDB: " + countRows, LENGTH_SHORT).show();
            return (countRows > 0);
        }
        return false;
    }

    // récupération des données
    ArrayList<Product> queryDB() {
        ArrayList<Product> products = new ArrayList<>();

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(MyDBOpenHelper.MY_TABLE_NAME1, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Product product = new Product();
                long id = cursor.getLong(0);
                product.setProduct_name(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD6)));
                product.setProduct_description(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD13)));
                product.setPath_image(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD5)));
                product.setProduct_price_ht(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD8)));
                product.setProduct_tva(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD9)));
                product.setProduct_price_ttc(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD10)));
                product.setProduct_stock(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD12)));
                product.setRef(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD7)));
                products.add(product);
            }
            cursor.close();
        }
        return products;
    }

    public void close() {
        db.close();
    }

    Product getProdect(String ref){
        Product product = new Product();
        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(MyDBOpenHelper.MY_TABLE_NAME1, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                product.setProduct_name(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD6)));
                product.setProduct_description(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD13)));
                product.setPath_image(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD5)));
                product.setProduct_price_ht(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD8)));
                product.setProduct_tva(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD9)));
                product.setProduct_price_ttc(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD10)));
                product.setProduct_stock(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.FIELD12)));
                product.setRef(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.FIELD7)));
            }
            cursor.close();
        }
        return product;

    }
}
