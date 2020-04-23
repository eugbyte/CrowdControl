package com.example.crowdControl.services;

import com.example.crowdControl.models.*;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    public List<Visit> findAll() {
        List<Visit> visits = visitRepository.findAll();
        return removeSelfReference(visits);
    }

    protected List<Visit> removeSelfReference(List<Visit> visits) {
        for (Visit visit : visits) {
            Shop shop = visit.getShop();
            shop.setVisits(null);
            Visitor visitor = visit.getVisitor();
            visitor.setVisits(null);
        }
        return visits;
    }

    protected Visit removeSelfReference(Visit visit) {
        Shop shop = visit.getShop();
        shop.setVisits(null);
        Visitor visitor = visit.getVisitor();
        visitor.setVisits(null);
        return visit;
    }

    @Async  //Annotation can work for entity that is many to one, but not vice versa
    public CompletableFuture<Visit> getById() {
        CompletableFuture<Visit> visitCf = visitRepository.getByVisitId(1);
        return visitCf.thenApply(visit -> removeSelfReference(visit));
    }
}
