package com.example.jkalanithi.light_me_up_sales;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static com.example.jkalanithi.light_me_up_sales.R.id.image;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product>{

    Context context;
    public ProductAdapter(Context context, List<Product> object){
        super(context,0,object);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview,parent, false);
        }
        ProductViewHolder viewHolder = (ProductViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ProductViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.ref = (TextView) convertView.findViewById(R.id.text_ref);
            viewHolder.ttc = (TextView) convertView.findViewById(R.id.ttc);
            viewHolder.image =(ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Product product = getItem(position);
        viewHolder.name.setText(product.getProduct_name());
        viewHolder.ref.setText(product.getRef());
        viewHolder.ttc.setText(product.getProduct_price_ttc().toString());
        Bitmap bitmap = rotateImageIfRequired(product.getPath_image());
        viewHolder.image.setImageBitmap(bitmap);

        return convertView;

    }

    private class ProductViewHolder{
        public TextView name;
        public TextView ref;
        public TextView ttc;
        public ImageView image;
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
