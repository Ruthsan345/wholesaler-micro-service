package com.example.wholesaler.model;

import java.util.ArrayList;

public class Wholesaler {

    int wholesale_id;
    String wholesale_name;
    ArrayList<Product> wholesale_products =new ArrayList<Product>();;


   public Wholesaler() {
    }

    public Wholesaler(int id, String name, ArrayList<Product> wholesalerList) {
        this.wholesale_id = id;
        this.wholesale_name = name;
        this.wholesale_products = new ArrayList<Product>();
    }

    public int getWholesale_id() {
        return wholesale_id;
    }

    public void setWholesale_id(int wholesale_id) {
        this.wholesale_id = wholesale_id;
    }

    public String getWholesale_name() {
        return wholesale_name;
    }

    public void setWholesale_name(String wholesale_name) {
        this.wholesale_name = wholesale_name;
    }

    public ArrayList<Product> getWholesale_products() {
        return wholesale_products;
    }

    public void setWholesale_products(Product wholesale_products) {
        this.wholesale_products.add(wholesale_products);
    }



}
