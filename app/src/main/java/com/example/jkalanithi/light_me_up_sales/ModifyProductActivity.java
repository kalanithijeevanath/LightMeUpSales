package com.example.jkalanithi.light_me_up_sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    Button mody_product;
    EditText name,ht,stock,info;
    ImageView imageView;
    File file;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);


        context = this.getApplicationContext();
        Bundle extras = getIntent().getExtras();
        final String ref = extras.getString("reference");




        dataBaseProduct = DataBaseProduct.getInstance(context);
        final Product product = dataBaseProduct.getProdect(ref);
        Button button1 = (Button) findViewById(R.id.product_image);
        mody_product = (Button)findViewById(R.id.mody_product);
        name = (EditText) findViewById(R.id.product_name);
        ht = (EditText) findViewById(R.id.product_price_ht);
        stock = (EditText) findViewById(R.id.product_stock);
        info = (EditText) findViewById(R.id.product_info);
        imageView = (ImageView) findViewById(R.id.imageView2);

        name.setText(product.getProduct_name());
        Log.d("name:",product.getProduct_name());
        ht.setText(product.getProduct_price_ht().toString());
        stock.setText(product.getProduct_stock().toString());
        info.setText(product.getProduct_description());
        Bitmap bitmap = rotateImageIfRequired(product.getPath_image());
        imageView.setImageBitmap(bitmap);



        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }

        });

        mody_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String ht1 = ht.getText().toString();
                String stock1 = stock.getText().toString();
                Integer ht_final = Integer.parseInt(ht1);
                Integer tva_final = 20;
                Integer stock_final = Integer.parseInt(stock1);
                Integer ttc = ht_final + ((ht_final*tva_final)/100);
                if(mCurrentPhotoPath != null){
                    file = new File(product.getPath_image());
                    file.delete();
                }
                if(mCurrentPhotoPath == null){
                    mCurrentPhotoPath = product.getPath_image();
                }
                boolean hasChanged = dataBaseProduct.updateDB(name.getText().toString(),ref,ht_final,tva_final,ttc,stock_final,info.getText().toString(),mCurrentPhotoPath);
                Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_SHORT).show();
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

    public Bitmap rotateImageIfRequired(String imagePath) {
        int degrees = 0;

        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degrees = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degrees = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degrees = 270;
                    break;
            }
        } catch (IOException e) {
            Log.e("ImageError", "Error in reading Exif data of " + imagePath, e);
        }

        BitmapFactory.Options decodeBounds = new BitmapFactory.Options();
        decodeBounds.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, decodeBounds);
        int numPixels = decodeBounds.outWidth * decodeBounds.outHeight;
        int maxPixels = 2048 * 1536; // requires 12 MB heap

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = (numPixels > maxPixels) ? 2 : 1;

        bitmap = BitmapFactory.decodeFile(imagePath, options);

        if (bitmap == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        return bitmap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            Bitmap bitmap = rotateImageIfRequired(mCurrentPhotoPath);
            image.setImageBitmap(bitmap);
        }
    }
}
