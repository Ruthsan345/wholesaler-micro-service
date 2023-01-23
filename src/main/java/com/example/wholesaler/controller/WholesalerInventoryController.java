package com.example.wholesaler.controller;

import com.example.wholesaler.api.WholesalerInventories;
import com.example.wholesaler.model.WholesalerInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("wholesaler/inventory/api/")
@RestController()
public class WholesalerInventoryController {
    @Autowired
    WholesalerInventories wholesalersInventoryOp;




    @PostMapping("/addProductToWholesaler")
    public String addProductToWholesaler(@RequestBody WholesalerInventory wholesalerInventory ) {

        wholesalersInventoryOp.addProductToWholesaler(wholesalerInventory);
        return "added product to wholesaler ";
    }

    @PutMapping("/updateProductToWholesaler")
    public ResponseEntity<String> updateProductToWholesaler(@RequestParam int inventoryId, @RequestParam int quantity , @RequestParam int price) {
        return wholesalersInventoryOp.updateProductToWholesaler(inventoryId,quantity,price);
    }




    @GetMapping("/getAllProductsByWholesalerId")
    public ArrayList<WholesalerInventory> getAllProductsByWholesalerId(@RequestParam int wholesalerId) {
        ArrayList<WholesalerInventory> pro = wholesalersInventoryOp.getAllProductsByWholesalerId(wholesalerId);
        return pro ;
    }


    @DeleteMapping("/deleteProductUsingInventoryId")
    public String deleteRetailer(@RequestParam int inventoryId) {

        return wholesalersInventoryOp.deleteProductUsingInventoryId(inventoryId);
    }
//
//    @GetMapping("/getAllProductsByWholesalerId")
//    public ArrayList<WarehouseInventory> getAllProductsByWarehouseId(@RequestParam int warehouseId) {
//        ArrayList<WarehouseInventory> pro = warehousesOp.getAllProductsByWarehouseId(warehouseId);
//        return pro ;
//    }
//
//    @DeleteMapping("/deleteProductFromWholesalerId")
//    public String deleteRetailer(@RequestParam int retailerId) {
//        return retailerOp.deleteRetailer(retailerId);
//    }



}
