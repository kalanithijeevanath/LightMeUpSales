package com.example.jkalanithi.light_me_up_sales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.jkalanithi.light_me_up_sales.MyThumbnailUtils.rotateImageIfRequired;
import static com.example.jkalanithi.light_me_up_sales.R.id.image;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    Context context;
    String s;
    public ProductAdapter(Context context, List<Product> object) {
        super(context, 0, object);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        s = ((MyApplication) this.getContext().getApplicationContext()).getSomeVariable();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        ProductViewHolder viewHolder = (ProductViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ProductViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.ref = (TextView) convertView.findViewById(R.id.text_ref);
            viewHolder.ttc = (TextView) convertView.findViewById(R.id.ttc);
            viewHolder.stock = (TextView) convertView.findViewById(R.id.stock);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }


        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Product product = getItem(position);
        if (s.equals("admin") == true) {
            if(Integer.parseInt(product.getProduct_stock().toString())==0) {
                viewHolder.stock.setTextColor(Color.RED);
            }
            else{
                viewHolder.stock.setTextColor(Color.parseColor("#ADCCE2"));
            }
        }
        viewHolder.name.setText("NAME : " + product.getProduct_name());
        viewHolder.ref.setText("REFERENCE : " + product.getRef());
        viewHolder.stock.setText("IN STOCK : " + product.getProduct_stock().toString());
        viewHolder.ttc.setText("PRICE TTC IN € : " + product.getProduct_price_ttc().toString());
        String realImagePath = product.getPath_image();
        File inImage = new File(realImagePath);
        String thumnailDirName = inImage.getParentFile().getAbsolutePath() + "/.thumbnails";
        String thumbnailImage = thumnailDirName + "/" + inImage.getName();
        File tmp = new File(thumbnailImage);
        if (tmp.exists() == false) {
            MyThumbnailUtils.createThumbnail(inImage);
        }
        Bitmap bitmap = rotateImageIfRequired(thumbnailImage);
        viewHolder.image.setImageBitmap(bitmap);
        return convertView;

    }

    private class ProductViewHolder {
        public TextView name;
        public TextView ref;
        public TextView ttc;
        public TextView stock;
        public ImageView image;

    }
    public boolean isEnable(int position){
        return false;
    }
}
