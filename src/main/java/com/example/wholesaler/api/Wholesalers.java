package com.example.wholesaler.api;


;

import com.example.wholesaler.model.Wholesaler;

import java.util.ArrayList;

public interface Wholesalers {
    Wholesaler displayWholesaler(int wholeSalerId);
    String addWholesaler(Wholesaler wholesaler);
    String deleteWholesaler(int wholeSalerId);
//    String allocateProductToWholesaler(int wholesalerId, int proid, int quantity, int price);
//    String updateWholesalerProduct(int wholesalerId, int proid, int quantity );
    ArrayList<Wholesaler> displayAllProduct();

    String allocateProductToWholesaler(int wholesalerId,int warehouseId, int proid, int quantity,int payingAmount, int price);

    String payPendingDue(String billId, int amount);
}
