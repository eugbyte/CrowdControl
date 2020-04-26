package com.example.crowdControl.repositories;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    Visitor findByNric(String nric);

    Visitor findByVisitorId(int visitorId);
    List<Visitor> findAll();

    Visitor findTopByOrderByVisitorIdDesc();
}
