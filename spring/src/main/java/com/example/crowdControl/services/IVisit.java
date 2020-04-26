package com.example.crowdControl.services;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.viewModels.ClusterViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface IVisit {
    List<Visit> findAll();
    List<Visit> findVisitsBetweenRange(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut);
    Visit createVisit(Visit _visit);
    CompletableFuture<Visit> getById(int visitId);
    List<Visit> findVisitsOfVisitor(int visitorId);
    List<Visit> findVisitsByShopId(int shopId);
}
