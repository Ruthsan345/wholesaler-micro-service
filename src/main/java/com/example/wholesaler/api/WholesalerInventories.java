package com.example.wholesaler.api;

import com.example.wholesaler.model.Wholesaler;
import com.example.wholesaler.model.WholesalerInventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public interface WholesalerInventories {
   String addProductToWholesaler(WholesalerInventory wholesalerInventory);

   ResponseEntity<String> updateProductToWholesaler(int inventoryId, int quantity, int price);

   ArrayList<WholesalerInventory> getAllProductsByWholesalerId(int warehouseId);

   String deleteProductUsingInventoryId(int inventoryId);

}
