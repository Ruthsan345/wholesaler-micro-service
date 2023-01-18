package com.example.wholesaler.controller;

import com.example.wholesaler.api.Wholesalers;
import com.example.wholesaler.helper.CsvReader;
import com.example.wholesaler.model.Wholesaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import com.example.wholesaler.service.impl.WholesalerOperation;

import java.io.IOException;



@RequestMapping("product/api/")
@RestController()
public class WholesalerController {
    @Autowired
    Wholesalers wholesalerOp;
    @Autowired
    CsvReader csvRead;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws IOException {
        csvRead.readWholesalerCsv(WholesalerOperation.wholesalerList);

        System.out.print("<--------------Wholesaler read from CSV-------------->");
    }

    @GetMapping("/displayWholesaler")
    public Wholesaler displayWholesaler(@RequestParam int wholesalerId) {
        return wholesalerOp.displayWholesaler(wholesalerId);
    }


    @PostMapping("/addWholesaler")
    public String addWholesaler(@RequestBody Wholesaler wholesaler) {
        return wholesalerOp.addWholesaler(wholesaler);
    }

    @DeleteMapping("/deleteWholesaler")
    public String deleteWholesaler(@RequestParam int wholersalerId) {
        return wholesalerOp.deleteWholesaler(wholersalerId);
    }

    @PutMapping("/updateWholesalerProduct")
    public String updateWholesaler(@RequestParam int wholersalerId ,@RequestParam int productId, @RequestParam int quantity) {
        return wholesalerOp.updateWholesalerProduct(wholersalerId, productId, quantity);
    }

    @PostMapping("/allocatingProductToWholesaler")
    public String allocateProductToWholesaler(@RequestParam int wholesalerId, @RequestParam  int proid,@RequestParam  int quantity,@RequestParam  int price) {
        return wholesalerOp.allocateProductToWholesaler(wholesalerId, proid, quantity, price);
    }
}
