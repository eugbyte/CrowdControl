package com.example.crowdControl.controllers;

import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.services.IVisitor;
import com.example.crowdControl.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("api/visitor")
public class VisitorController {

    @Autowired
    private IVisitor visitorService;

    @GetMapping()
    public Callable<ResponseEntity> getAllVisitors() {
        return () -> {
            List<Visitor> visitors = visitorService.findAllVisitors();
            return ResponseEntity.ok(visitors);
        };
    }

    @GetMapping("{visitorId}")
    public Callable<ResponseEntity> getVisitor(@PathVariable int visitorId) {
        return () -> {
            Visitor visitor = visitorService.findVisitorById(visitorId);
            return ResponseEntity.ok(visitor);
        };
    }

}
