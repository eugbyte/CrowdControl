package com.example.crowdControl.services;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService implements IVisitor{

    @Autowired
    VisitorRepository visitorRepository;

    public List<Visitor> findAllVisitors() {
        List<Visitor> visitors = visitorRepository.findAll();
        return removeSelfReferenceLoop(visitors);
    }

    public Visitor findVisitorById(int visitorId) {
        Visitor visitor = visitorRepository.findByVisitorId(visitorId);
        return removeSelfReferenceLoop(visitor);
    }

    protected Visitor removeSelfReferenceLoop(Visitor visitor) {
        List<Visit> visits = visitor.getVisits();
        visits.forEach(visit -> {
            visit.setVisitor(null);
            Shop shop = visit.getShop();
            shop.setVisits(null);
        });
        return visitor;
    }

    protected List<Visitor> removeSelfReferenceLoop(List<Visitor> visitors) {
        visitors.forEach(visitor -> removeSelfReferenceLoop(visitor));
        return visitors;
    }
}
