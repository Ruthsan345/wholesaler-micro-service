package com.example.wholesaler.model;

public class Product {
     int proId;
     String proName;
     int stock;
     int price;
     int gstPercentage;
//    NavigableMap<Integer,Integer> discount=new TreeMap<Integer, Integer>();

   public Product(){}
   public Product(int id, String name, int sto, int pri, int gst){
        this.proId = id;
        this.proName = name;
        this.stock = sto;
        this.price = pri;
        this.gstPercentage = gst;
    }


    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(int gstPercentage) {
        this.gstPercentage = gstPercentage;
    }
}
