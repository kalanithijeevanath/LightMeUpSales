package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jkalanithi on 14/08/2017.
 */

public class ModifyProductActivity extends Activity {

    private DataBaseProduct dataBaseProduct;
    Context context;
    private static final int CAMERA_PIC_REQUEST = 001;
    String mCurrentPhotoPath;
    Button add_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_add);


        context = this.getApplicationContext();

        dataBaseProduct = DataBaseProduct.getInstance(context);
        Button button1 = (Button) findViewById(R.id.product_image);
        add_product = (Button)findViewById(R.id.add_product);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }

        });

        add_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                boolean hasChanged = dataBaseProduct.insertDB(name.getText().toString(),ref.getText().toString(),ht_final,tva_final,ttc,stock_final,info.getText().toString(),mCurrentPhotoPath);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
