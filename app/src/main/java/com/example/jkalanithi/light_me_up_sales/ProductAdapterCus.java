package com.example.jkalanithi.light_me_up_sales;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkalanithi on 23/08/2017.
 */

public class ProductAdapterCus extends ArrayAdapter<Product> {

    Context context;
    int i = 0;

    public ProductAdapterCus(Context context, List<Product> object) {
        super(context, 0, object);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_cus, parent, false);
        }
        ProductViewHolder viewHolder = (ProductViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ProductViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check);
            viewHolder.ttc = (TextView) convertView.findViewById(R.id.ttc);
            viewHolder.stock = (TextView) convertView.findViewById(R.id.stock);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Product product = getItem(position);
        viewHolder.name.setText("NOM : " + product.getProduct_name());
        viewHolder.stock.setText("STOCK : " + product.getProduct_stock().toString());
        viewHolder.ttc.setText("PRIX TTC En € : " + product.getProduct_price_ttc().toString());
        Bitmap bitmap = rotateImageIfRequired(product.getPath_image());
        viewHolder.image.setImageBitmap(bitmap);
        viewHolder.checkBox.setTag(position);
        i++;
        if (i % 2 == 0) {
            viewHolder.checkBox.setEnabled(false);
        }
        return convertView;

    }

    private class ProductViewHolder {
        public TextView name;
        public TextView ttc;
        public TextView stock;
        public ImageView image;
        public CheckBox checkBox;
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
}