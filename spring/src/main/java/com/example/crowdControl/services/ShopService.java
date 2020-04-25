package com.example.crowdControl.services;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.ssl.Debug;

import javax.management.OperationsException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ShopService implements IShop {
    @Autowired
    protected ShopRepository shopRepository;

    public CompletableFuture<Shop> getByShopId(int shopId) {
        CompletableFuture<Shop> shopCf = shopRepository.getByShopId(shopId)
                .thenApply(shop -> removeSelfReferenceLoop(shop));
        return shopCf;
    }

    public List<Shop> findAllShops() {
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
            List<Visit> visits = shop.getVisits();
            visits.forEach(visit -> {
                visit.setShop(null);
                Visitor visitor = visit.getVisitor();
                visitor.setVisits(null);
            } );
        }
        return shops;
    }

    public Shop createShop(Shop shop) {
        shopRepository.save(shop);
        return shopRepository.findTopByOrderByShopIdDesc();
    };

    public Shop updateShop(Shop shop) {
        int shopId = shop.getShopId();
        Shop shopToUpdate = shopRepository.findByShopId(shopId);

        shopToUpdate.setName(shop.getName());
        shopRepository.save(shopToUpdate);
        return shopToUpdate;
    }

    public void deleteShop(int shopId) {
        shopRepository.deleteById(shopId);
    }

    protected Shop removeSelfReferenceLoop(Shop shop) {
        List<Visit> visits = shop.getVisits();
        visits.forEach(visit -> {
            visit.setShop(null);
            Visitor visitor = visit.getVisitor();
            visitor.setVisits(null);
        });
        return shop;
    }

    protected List<Shop> removeSelfReferenceLoop(List<Shop> shops) {
        for (Shop shop: shops) {
            List<Visit> visits = shop.getVisits();
            visits.forEach(visit -> {
                visit.setShop(null);
                Visitor visitor = visit.getVisitor();
                visitor.setVisits(null);
            });
        }
        return shops;
    }
}
