package com.example.jkalanithi.light_me_up_sales;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.db.MyDBOpenHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Jkalanithi on 09/08/2017.
 */

public class AddProductActivity extends Activity {


    SQLiteDatabase db;
    Context context;
    private static final int CAMERA_PIC_REQUEST = 001;
    String mCurrentPhotoPath;
    Button refreshButton;
    ArrayList <Product> products ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_add);

        initDB();
        context = this.getApplicationContext();

        Button button1 = (Button) findViewById(R.id.product_image);
        refreshButton = (Button) findViewById(R.id.refresh);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    public void displayData(View v) {
        setChangeData(false);

        TextView dataText = (TextView) findViewById(R.id.dataText);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        products = queryDB();

        /*dataText.setText(products.get(0).getProduct_name().concat("/").concat(products.get(0).getRef()).concat("/").concat(products.get(0).getProduct_description()).concat("/").concat(products.get(0).getProduct_price_ht().toString()).concat("€/")
                .concat(products.get(0).getProduct_tva().toString()).concat("%/").concat(products.get(0).getProduct_price_ttc().toString()).concat("€/").concat(products.get(0).getProduct_stock().toString()));
        imageView.setImageURI(Uri.parse(products.get(0).getPath_image()));*/
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,R.id.dataText, new String[]{products.get(0).getProduct_name().concat("  ").concat(products.get(0).getRef().concat("" +
                "       ").concat(products.get(0).getProduct_description())), products.get(1).getProduct_name().concat("  ").concat(products.get(1).getRef().concat("" +
                "       ").concat(products.get(1).getProduct_description()))});
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
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

    public void add(View v) {

        EditText name = (EditText) findViewById(R.id.product_name);
        EditText ref = (EditText) findViewById(R.id.product_ref);
        EditText ht = (EditText) findViewById(R.id.product_price_ht);
        EditText tva = (EditText) findViewById(R.id.product_tva);
        EditText stock = (EditText) findViewById(R.id.product_stock);
        EditText info = (EditText) findViewById(R.id.product_info);

        String ht1 = ht.getText().toString();
        String tva1 = tva.getText().toString();
        String stock1 = stock.getText().toString();
        Integer ht_final = Integer.parseInt(ht1);
        Integer tva_final = Integer.parseInt(tva1);
        Integer stock_final = Integer.parseInt(stock1);
        Integer ttc = ht_final + ((ht_final*tva_final)/100);

        boolean hasChanged = insertDB(name.getText().toString(),ref.getText().toString(),ht_final,tva_final,ttc,stock_final,info.getText().toString(),mCurrentPhotoPath);
    }
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            image.setImageURI(Uri.parse(mCurrentPhotoPath));
        }
    }

    public void initDB() {
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(getApplicationContext());

        try {
            db = dbHelper.getReadableDatabase(); // Database en lecture seule
        }
        catch (SQLiteException e) {

            db = openOrCreateDatabase(MyDBOpenHelper.DB_NAME, MODE_PRIVATE, null);
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
                Toast.makeText(context, "insertDB: " + id, LENGTH_SHORT).show();
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
            Toast.makeText(context, "updateDB: " + countRows, LENGTH_SHORT).show();
            return (countRows > 0);
        }
        return false;
    }
    //suppression d'une ligne
    public boolean deleteDB(String ref) {
        if (db != null && db.isOpen() && !db.isReadOnly()) {
            int countRows = db.delete(MyDBOpenHelper.MY_TABLE_NAME1, "REF = ?", new String[]{String.valueOf(ref)});
            Toast.makeText(context, "deleteDB: " + countRows, LENGTH_SHORT).show();
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
}

