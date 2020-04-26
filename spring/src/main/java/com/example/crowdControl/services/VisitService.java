package com.example.crowdControl.services;

import com.example.crowdControl.models.*;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.repositories.ShopRepository;
import com.example.crowdControl.repositories.VisitRepository;
import com.example.crowdControl.repositories.VisitorRepository;
import com.example.crowdControl.viewModels.ClusterViewModel;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.security.ssl.Debug;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class VisitService implements IVisit {
    @Autowired
    protected VisitRepository visitRepository;
    @Autowired
    protected VisitorRepository visitorRepository;
    @Autowired
    protected ShopRepository shopRepository;

    private final Comparator<Visit> visitDateInComparator = Comparator.comparing(Visit::getDateTimeIn).reversed();

    public List<Visit> findAll() {
        List<Visit> visits = visitRepository.findAll();
        visits = sortVisitsByDescendingByDateTimeIn(visits);
        return removeSelfReference(visits);
    }

    public List<Visit> findVisitsOfVisitor(int visitorId) {
        List<Visit> visits = visitRepository.findByVisitor_visitorId(visitorId);
        visits = sortVisitsByDescendingByDateTimeIn(visits);
        return removeSelfReference(visits);
    }

    public List<Visit> findVisitsBetweenRange(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut) {
        List<Visit> visits = visitRepository.findByDateTimeInAfterAndDateTimeOutBefore(dateTimeIn, dateTimeOut);
        return removeSelfReference(visits);
    }

    public Visit createVisit(Visit _visit) {
        String nric = _visit.getVisitor().getNric();
        String name = _visit.getVisitor().getName();
        int shopId = _visit.getShop().getShopId();
        LocalDateTime dateTimeIn = _visit.getDateTimeIn();
        LocalDateTime dateTimeOut = _visit.getDateTimeOut();
        Debug.println("dateTimeIn", String.valueOf(dateTimeIn));
        Debug.println("dateTimeOut", String.valueOf(dateTimeOut));

        //Check if visitor exists. If not, create the visitor
        Visitor visitor = visitorRepository.findByNric(nric);

        if (visitor == null) {
            visitor = createVisitor(nric, name);
        }

        //The visit from the request body either records entry OR exit, but not both
        if (dateTimeIn == null && dateTimeOut != null) {
            Visit latestVisit = visitRepository.findTopByVisitor_visitorIdAndShop_shopIdOrderByVisitIdDesc(visitor.getVisitorId(), shopId);
            latestVisit.setDateTimeOut(dateTimeOut);
            visitRepository.save(latestVisit);
            return removeSelfReference(latestVisit);
        }

        //Get the shop
        Shop shop = shopRepository.findByShopId(shopId);

        //Create the new visit
        Visit visit = new Visit();
        visit.setShop(shop);
        visit.setVisitor(visitor);

        if (dateTimeIn != null && dateTimeOut == null) {
            visit.setDateTimeIn(dateTimeIn);
        } else {
            Debug.println("500", "json requestBody error");
        }
        visitRepository.save(visit);

        //Retrieve latest created visit
        Visit createdVisit = visitRepository.findTopByOrderByVisitIdDesc();
        return removeSelfReference(createdVisit);
    }

    public List<Visit> findVisitsByShopId(int shopId) {
        List<Visit> visits = visitRepository.findByShop_shopId(shopId);
        visits = sortVisitsByDescendingByDateTimeIn(visits);
        return removeSelfReference(visits);
    }

    @Async  //Annotation can work for entity that is many to one, but not vice versa
    public CompletableFuture<Visit> getById(int visitId) {
        CompletableFuture<Visit> visitCf = visitRepository.getByVisitId(visitId);
        return visitCf.thenApply(visit -> removeSelfReference(visit));
    }

    protected List<Visit> sortVisitsByDescendingByDateTimeIn(List<Visit> visits) {
        Comparator<Visit> visitDateInComparator = Comparator.comparing(Visit::getDateTimeIn).reversed();
        Collections.sort(visits, visitDateInComparator);
        return visits;
    }

    protected Visitor createVisitor(String nric, String name) {
        Visitor visitor = new Visitor();
        visitor.setNric(nric);
        visitor.setName(name);
        visitorRepository.save(visitor);
        visitor = visitorRepository.findTopByOrderByVisitorIdDesc();
        return visitor;
    }

    protected List<Visit> removeSelfReference(List<Visit> visits) {
        for (Visit visit : visits) {
            visit = removeSelfReference(visit);
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
}
