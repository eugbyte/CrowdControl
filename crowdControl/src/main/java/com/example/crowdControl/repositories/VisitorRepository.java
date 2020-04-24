package com.example.crowdControl.repositories;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    Visitor findByNric(String nric);

    Visitor findTopByOrderByVisitorId();
}
