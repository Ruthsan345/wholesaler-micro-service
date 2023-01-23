package com.example.wholesaler.service.impl;

import com.example.wholesaler.api.WholesalerInventories;
import com.example.wholesaler.model.WholesalerInventory;
import com.example.wholesaler.repository.WholesalerInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WholesalerInventoryOperation implements WholesalerInventories {
@Autowired
    WholesalerInventoryRepository wholesalerInventoryRepository;

    @Override
    public String addProductToWholesaler(WholesalerInventory wholesalerInventory) {
         wholesalerInventoryRepository.save(wholesalerInventory);
        return "Succesfully added";
    }

    @Override
    public ResponseEntity<String> updateProductToWholesaler(int inventoryId, int quantity, int price) {
        WholesalerInventory wholesalerInventory = wholesalerInventoryRepository.findById(inventoryId);
        wholesalerInventory.setStock(quantity);
        wholesalerInventory.setPrice(price);
        wholesalerInventoryRepository.save(wholesalerInventory);
        return new ResponseEntity<>( "Updated Product to wholesaler", HttpStatus.OK);

    }

    @Override
    public ArrayList<WholesalerInventory> getAllProductsByWholesalerId(int wholesalerId) {

        return wholesalerInventoryRepository.findByWholesalerid(wholesalerId);
    }

    @Override
    public String deleteProductUsingInventoryId(int inventoryId) {
        wholesalerInventoryRepository.deleteById(inventoryId);
        return "Deleted product from wholesellers";
    }


}
