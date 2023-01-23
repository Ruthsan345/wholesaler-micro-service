package com.example.wholesaler.repository;

import com.example.wholesaler.model.Wholesaler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WholesalerRepository extends JpaRepository<Wholesaler, Integer> {
    Wholesaler findById(int Id);

}
