package com.example.jkalanithi.light_me_up_sales.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jkalanithi.light_me_up_sales.CartProduct;
import com.example.jkalanithi.light_me_up_sales.R;

import java.util.List;

/**
 * Created by Jkalanithi on 31/08/2017.
 */

public class ProductCartAdapter extends ArrayAdapter<CartProduct> {
    Context context;

    public ProductCartAdapter(Context context, List<CartProduct> object) {
        super(context, 0,object);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_selection, parent, false);
        }
        ProductCartAdapter.ProductViewHolder viewHolder = (ProductCartAdapter.ProductViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ProductCartAdapter.ProductViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_product);
            viewHolder.ttc = (TextView) convertView.findViewById(R.id.price);
            viewHolder.nb_choix = (TextView) convertView.findViewById(R.id.nb_choix);
            convertView.setTag(viewHolder);
        }


        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        CartProduct cartProduct = getItem(position);
        viewHolder.name.setText("NAME : " + cartProduct.getName());
        viewHolder.ttc.setText("PRICE PER UNIT € : " + cartProduct.getPrice().toString());
        viewHolder.nb_choix.setText("QUANTITY :" + cartProduct.getNb_choix().toString());
        return convertView;

    }

    private class ProductViewHolder {
        public TextView name;
        public TextView ttc;
        public TextView nb_choix;

    }
}
