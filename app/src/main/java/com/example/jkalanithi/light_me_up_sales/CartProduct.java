package com.example.jkalanithi.light_me_up_sales;

/**
 * Created by Jkalanithi on 31/08/2017.
 */

public class CartProduct {

        private String name;
        private Double price;
        private Integer nb_choix;

        public CartProduct(String name, Double price ,Integer nb_choix) {
            this.name = name;
            this.price = price;
            this.nb_choix = nb_choix;
        }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNb_choix() {
        return nb_choix;
    }

    public void setNb_choix(Integer nb_choix) {
        this.nb_choix = nb_choix;
    }

}
