package com.example.crowdControl.controllers;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.services.IShop;
import com.example.crowdControl.viewModels.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/shop")
public class ShopController {

    @Autowired
    private IShop shopService;

    @GetMapping()
    public Callable<List<Shop>> getAllShops() {
        return () -> shopService.findAllShops();
    }

    @GetMapping("{shopId}")
    public CompletableFuture<Shop> getShopByShopId(@PathVariable int shopId) {
        return shopService.getByShopId(shopId);
    }

    @PostMapping()
    public Callable<ResponseEntity> createShop(@RequestBody Shop shop) {
        return () -> {
            try {
                Shop createdShop = shopService.createShop(shop);
                ResponseEntity<Shop> responseEntity = ResponseEntity.ok(createdShop);
                return responseEntity;
            } catch (Exception exception) {
                ResponseEntity<String> errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(exception.toString());
                return errorResponse;
            }
        };
    }

    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping()
    public Callable<ResponseEntity> updateShop(@RequestBody Shop shop) {

        return () -> {
            try {
                Shop updatedShop = shopService.updateShopName(shop);
                return ResponseEntity.ok(updatedShop);
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body("not found " + exception.toString());
            }
        };
    }

    @DeleteMapping(value = "{shopId}", produces = "application/json")
    public Callable<ResponseEntity> deleteShop(@PathVariable int shopId) {
        return () -> {
            try {
                shopService.deleteShop(shopId);
                return ResponseEntity.ok(new StringResponse(shopId + " deleted"));
            } catch (Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(exception.toString());
            }
        };
    }


}
