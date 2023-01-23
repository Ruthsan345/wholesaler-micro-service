package com.example.wholesaler.repository;

import com.example.wholesaler.model.WholesalerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface WholesalerInventoryRepository extends JpaRepository<WholesalerInventory, Integer> {
    WholesalerInventory findById(int Id);

    ArrayList<WholesalerInventory> findByWholesalerid(int wholesalerId);
}
