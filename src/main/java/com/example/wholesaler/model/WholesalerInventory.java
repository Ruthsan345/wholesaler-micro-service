package com.example.wholesaler.model;


import javax.persistence.*;

@Entity
@Table(name = "wholesaler_inventory")

public class WholesalerInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(name = "warehouse_id")
    public int warehouseid;
    @Column(name = "wholesaler_id")
    public int wholesalerid;
    @Column(name = "product_id")
    public int product_id;
    @Column(name = "stock")
    public int stock;
    @Column(name = "price")
    public int price;

    public WholesalerInventory(){}

    public WholesalerInventory(int id, int warehouseid, int wholesalerid, int product_id, int stock, int price) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.wholesalerid = wholesalerid;
        this.product_id = product_id;
        this.stock = stock;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(int warehouseid) {
        this.warehouseid = warehouseid;
    }

    public int getWholesalerid() {
        return wholesalerid;
    }

    public void setWholesalerid(int wholesalerid) {
        this.wholesalerid = wholesalerid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

}
