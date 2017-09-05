package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jkalanithi.light_me_up_sales.Data_Base.DataBaseProduct;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jkalanithi on 09/08/2017.
 */

public class AddProductActivity extends Activity {


    private DataBaseProduct dataBaseProduct;
    Context context;
    private static final int CAMERA_PIC_REQUEST = 001;
    String mCurrentPhotoPath ="";
    Button add_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_add);


        context = this.getApplicationContext();

        dataBaseProduct = DataBaseProduct.getInstance(context);
        final Button button1 = (Button) findViewById(R.id.product_image);
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
                EditText stock = (EditText) findViewById(R.id.product_stock);
                EditText info = (EditText) findViewById(R.id.product_info);
                boolean state;


                if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(ref.getText().toString()) || TextUtils.isEmpty(ht.getText().toString()) || TextUtils.isEmpty(info.getText().toString()) || mCurrentPhotoPath.isEmpty()  ) {
                    if (TextUtils.isEmpty(name.getText().toString())){
                        name.setError("Product name cannot be empty");
                    }
                    if (TextUtils.isEmpty(ref.getText().toString())){
                        ref.setError("Product reference cannot be empty");
                    }
                    if(TextUtils.isEmpty(ht.getText().toString())){
                        ht.setError("Product price without tax cannot be empty");
                    }
                    if(TextUtils.isEmpty(info.getText().toString())){
                        info.setError("Product information cannot be empty");
                    }
                    if(mCurrentPhotoPath.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Photo requested", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                if(ref.getText().toString().isEmpty() == false){
                    state = dataBaseProduct.CheckIsExist(ref.getText().toString());
                    if (state == true){
                        ref.setError("Product reference already exist");
                        return;
                    }
                }


                String ht1 = ht.getText().toString();
                String stock1 = stock.getText().toString();
                Double ht_final = Double.parseDouble(ht1);
                Integer tva_final = 20;
                Integer stock_final = Integer.parseInt(stock1);
                if (stock_final == null){
                    stock_final = 0;
                }
                Double ttc = ht_final + ((ht_final*tva_final)/100);
                boolean hasChanged = dataBaseProduct.insertDB(name.getText().toString(),ref.getText().toString(),ht_final,tva_final,ttc,stock_final,info.getText().toString(),mCurrentPhotoPath);
                Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_SHORT).show();
                finish();
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            Bitmap bitmap = MyThumbnailUtils.rotateImageIfRequired(mCurrentPhotoPath);
            MyThumbnailUtils.createThumbnail(new File(mCurrentPhotoPath));
            image.setImageBitmap(bitmap);
        }
    }


}

