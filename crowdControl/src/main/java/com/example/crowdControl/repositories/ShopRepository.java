package com.example.crowdControl.repositories;

import com.example.crowdControl.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    CompletableFuture<Shop> getByShopId(int shopId);
    Shop findByShopId(int shopId);
    List<Shop> findAll();
    Shop findTopByOrderByShopIdDesc();

    @Override
    void deleteById(Integer shopId);

    @Override
    boolean existsById(Integer shopId);
}
