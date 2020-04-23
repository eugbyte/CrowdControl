package com.example.crowdControl.repositories;

import com.example.crowdControl.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findAll();

    CompletableFuture<Visit> getByVisitId(int visitId);
}
