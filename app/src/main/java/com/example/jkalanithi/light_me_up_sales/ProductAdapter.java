package com.example.jkalanithi.light_me_up_sales;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product>{

    Context context;
    public ProductAdapter(Context context, int resource, List<Product> object){
        super(context,resource,object);

        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View ligne = layoutInflater.inflate(R.layout.ligne, null);
        TextView title =(TextView) ligne.findViewById(R.id.label);
        TextView content = (TextView) ligne.findViewById(R.id.promotion);

        Product product = getItem(position);
        title.setText("Product_Name");
        content.setText("info_Product");

        return ligne;
    }

}
