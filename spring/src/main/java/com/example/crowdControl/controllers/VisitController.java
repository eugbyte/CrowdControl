package com.example.crowdControl.controllers;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.services.IVisit;
import com.example.crowdControl.viewModels.ClusterViewModel;
import com.example.crowdControl.viewModels.VisitViewModel;
import com.example.crowdControl.viewModels.VisitorShopViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/visit")
public class VisitController {

    @Autowired private IVisit visitService;

    @GetMapping("shop/{shopId}")
    public Callable<ResponseEntity <List <Visit>>> getVisitsByShopId(@PathVariable int shopId) {
        return () -> {
            List<Visit> visits = visitService.findVisitsByShopId(shopId);
            return ResponseEntity.ok(visits);
        };    }



    @PostMapping()
    public Callable<ResponseEntity> createVisit(@RequestBody VisitViewModel visitViewModel) {
        return ()-> {
            //Currently facing serialization issue from JS Date to Java DateTimeNow
            //Will work on it later, for now use shortcut
            Visit visit = new Visit();
            visit.setVisitor(visitViewModel.getVisitor());
            visit.setShop((visitViewModel.getShop()));
            if (visitViewModel.entryOrExit.equals("ENTRY"))
                visit.setDateTimeIn(LocalDateTime.now());
            else if (visitViewModel.entryOrExit.equals("EXIT"))
                visit.setDateTimeOut(LocalDateTime.now());

            Visit createdVisit = visitService.createVisit(visit);
            return ResponseEntity.ok(createdVisit);
        };
    }

    @GetMapping()
    public Callable <ResponseEntity< List<Visit>> > getAllVisits() {
        return () -> {
            List<Visit> visits = visitService.findAll();
            ResponseEntity<List<Visit>> response = new ResponseEntity<>(visits, HttpStatus.OK);
            return response;
        };
    }

    @GetMapping("visitor/{visitorId}")
    public Callable<ResponseEntity> getVisitsOfVisitor(@PathVariable int visitorId) {
        return () -> {
            List<Visit> visits = visitService.findVisitsOfVisitor(visitorId);
            return ResponseEntity.ok(visits);
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

}
