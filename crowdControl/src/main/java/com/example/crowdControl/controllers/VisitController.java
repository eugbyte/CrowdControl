package com.example.crowdControl.controllers;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping()
    public Callable< ResponseEntity< List<Visit>> > getAllVisits() {
        Callable< ResponseEntity< List<Visit>> > callable = () -> {
            List<Visit> visits = visitService.findAll();
            ResponseEntity<List<Visit>> response = new ResponseEntity<List<Visit>>(visits, HttpStatus.OK);
            return response;
        };
        return callable;
    }

    @GetMapping("future")
    public CompletableFuture<Visit> getVisitById() {
        return visitService.getById();
    }
}
