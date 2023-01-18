package com.example.wholesaler.api;


;

import com.example.wholesaler.model.Product;
import com.example.wholesaler.model.Wholesaler;

import java.util.ArrayList;

public interface Wholesalers {
    Wholesaler displayWholesaler(int wholeSalerId);
    String addWholesaler(Wholesaler wholesaler);
    String deleteWholesaler(int wholeSalerId);
    String allocateProductToWholesaler(int wholesalerId, int proid, int quantity, int price);
    String updateWholesalerProduct(int wholesalerId, int proid, int quantity );
}
