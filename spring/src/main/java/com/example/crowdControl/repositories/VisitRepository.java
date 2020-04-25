package com.example.crowdControl.repositories;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findAll();

    CompletableFuture<Visit> getByVisitId(int visitId);

    Visit findTopByOrderByVisitIdDesc();

    List<Visit> findByVisitor_visitorId(Integer visitorId);

    List<Visit> findByDateTimeInAfterAndDateTimeOutBefore(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut);

    List<Visit> findByShop_shopId(int shopId);
}
