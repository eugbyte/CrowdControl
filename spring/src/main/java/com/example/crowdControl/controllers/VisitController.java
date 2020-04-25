package com.example.crowdControl.controllers;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.services.IVisit;
import com.example.crowdControl.services.VisitService;
import com.example.crowdControl.viewModels.OverlapViewModel;
import com.example.crowdControl.viewModels.VisitorShopViewModel;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.ssl.Debug;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/visit")
public class VisitController {

    @Autowired private IVisit visitService;

    @GetMapping()
    public Callable <ResponseEntity< List<Visit>> > getAllVisits() {
        return () -> {
            List<Visit> visits = visitService.findAll();
            ResponseEntity<List<Visit>> response = new ResponseEntity<>(visits, HttpStatus.OK);
            return response;
        };
    }

    @GetMapping("{visitId}")
    public CompletableFuture<Visit> getVisitById(@PathVariable int visitId) {
        return visitService.getById(visitId);
    }

    @GetMapping("range")
    public Callable <ResponseEntity <List <Visit>>> getVisitsBetweenRange(
            @RequestParam(name="dateTimeIn") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime dateTimeIn,
            @RequestParam(name="dateTimeOut") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime dateTimeOut
            ) {
        return () -> {
            List<Visit> visits = visitService.findVisitsBetweenRange(dateTimeIn, dateTimeOut);
            return ResponseEntity.ok(visits);
        };
    }

    @GetMapping("overlaps")
    public Callable <ResponseEntity> getAllOverlaps(@RequestParam(required = false) LocalDate localDate) {
        return () -> {
            try {
                List<OverlapViewModel> vms = visitService.getAllOverLaps(Optional.ofNullable(localDate));
                return ResponseEntity.ok(vms);
            } catch (Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.toString());
            }

        };
    }

    @PostMapping()
    public Callable<ResponseEntity> createVisit(@RequestBody VisitorShopViewModel visitorShopViewModel) {
        return ()-> {
            Visitor visitor = visitorShopViewModel.visitor;
            Shop shop = visitorShopViewModel.shop;
            Visit visit = visitService.createVisit(visitor, shop);
            return ResponseEntity.ok(visit);
        };
    }


}
