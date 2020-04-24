package com.example.crowdControl.services;

import com.example.crowdControl.models.Shop;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IShop {
    CompletableFuture<Shop> getByShopId(int shopId);
    List<Shop> findAllShops();
    Shop createShop(Shop shop);
    Shop updateShop(Shop shop);
    void deleteShop(int shopId);
}
