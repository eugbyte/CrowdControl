package com.example.crowdControl.controllers;

import com.example.crowdControl.services.ClusterService;
import com.example.crowdControl.viewModels.ClusterViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("api/cluster")
public class ClusterController {

    @Autowired
    protected ClusterService clusterService;

    @GetMapping("{shopId}")
    public Callable<ResponseEntity> getClustersOfShop(@PathVariable int shopId) {
        return () -> {
            List<ClusterViewModel> vms = clusterService.findClustersOfShop(shopId);
            return ResponseEntity.ok(vms);
        };
    }

    @GetMapping("overlaps")
    public Callable <ResponseEntity> getAllOverlaps(@RequestParam(required = false) LocalDate localDate) {
        return () -> {
            try {
                List<ClusterViewModel> vms = clusterService.getAllClusters();
                return ResponseEntity.ok(vms);
            } catch (Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.toString());
            }

        };
    }
}
