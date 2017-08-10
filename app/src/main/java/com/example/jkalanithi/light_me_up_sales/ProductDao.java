package com.example.jkalanithi.light_me_up_sales;

import java.util.ArrayList;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class ProductDao {
    public static DataBaseProduct dataBaseProduct = new DataBaseProduct();

    static {
            dataBaseProduct.initDB();
    }

    public static boolean put (ArrayList<Product> products){

        for (Product product : products){
            if(ProductDao.dataBaseProduct.insertDB(product.getProduct_name(),product.getRef(),product.getProduct_price_ht(),product.getProduct_tva(),product.getProduct_price_ttc(),product.getProduct_stock(),product.getProduct_description(),product.getPath_image()))
               return false;

        }
        return true;
    }
}
