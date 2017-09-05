package com.example.jkalanithi.light_me_up_sales.Model;

/**
 * Created by Jkalanithi on 08/08/2017.
 */

public class Product {

    public String product_name;
    public String ref;
    public Double product_price_ht;
    public Integer product_tva;
    public Double product_price_ttc;
    public Integer product_stock;
    public String product_description;
    public String path_image;


    public Product (String product_name,String ref,Double product_price_ttc,String path_image,Integer stock){
        super();
        this.product_name = product_name;
        this.ref = ref;
        this.product_price_ttc = product_price_ttc;
        this.path_image = path_image;
        this.product_stock = stock;
    }
    public Product (){
        this.product_name = " ";
        this.ref = " ";
        this.product_price_ht = 0.0;
        this.product_tva = 0;
        this.product_price_ttc = 0.0;
        this.product_stock = 0;
        this.product_description = " ";
        this.path_image = " ";
    }
    public Product (String productName, String ref, Double productPriceHt, Integer productTva, Double productPriceTtc, Integer productStock, String productDescription, String pathImageLocal){
        this.product_name = productName;
        this.ref = productName;
        this.product_price_ht = productPriceHt;
        this.product_tva = productTva;
        this.product_price_ttc = productPriceTtc;
        this.product_stock = productStock;
        this.product_description = productDescription;
        this.path_image = pathImageLocal;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price_ht(Double product_price_ht) {
        this.product_price_ht = product_price_ht;
    }

    public void setProduct_price_ttc(Double product_price_ttc) {
        this.product_price_ttc = product_price_ttc;
    }

    public void setProduct_stock(Integer product_stock) {
        this.product_stock = product_stock;
    }

    public void setProduct_tva(Integer product_tva) {
        this.product_tva = product_tva;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Double getProduct_price_ht() {
        return product_price_ht;
    }

    public Double getProduct_price_ttc() {
        return product_price_ttc;
    }

    public Integer getProduct_stock() {
        return product_stock;
    }

    public Integer getProduct_tva() {
        return product_tva;
    }

    public String getPath_image() {
        return path_image;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getRef() {
        return ref;
    }

}
