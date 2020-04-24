package com.example.crowdControl.services;

import com.example.crowdControl.models.*;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.repositories.ShopRepository;
import com.example.crowdControl.repositories.VisitRepository;
import com.example.crowdControl.repositories.VisitorRepository;
import com.example.crowdControl.viewModels.OverlapViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class VisitService implements IVisit {
    @Autowired private VisitRepository visitRepository;
    @Autowired private VisitorRepository visitorRepository;
    @Autowired private ShopRepository shopRepository;

    public List<Visit> findAll() {
        List<Visit> visits = visitRepository.findAll();
        return removeSelfReference(visits);
    }

    public List<Visit> findVisitsBetweenRange(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut) {
        List<Visit> visits = visitRepository.findByDateTimeInAfterAndDateTimeOutBefore(dateTimeIn, dateTimeOut);
        return removeSelfReference(visits);
    }

    public Visit createVisit(Visitor _visitor, Shop _shop) {
        String nric = _visitor.getNric();
        String name = _visitor.getName();
        int shopId = _shop.getShopId();

        //Check if visitor exists. If not, create the visitor
        Visitor visitor = visitorRepository.findByNric(nric);

        if (visitor == null) {
            visitor = createVisitor(nric, name);
        }

        //Get the shop
        Shop shop = shopRepository.findByShopId(shopId);

        //Create the new visit
        Visit visit = new Visit();
        visit.setDateTimeIn(LocalDateTime.now());
        visit.setShop(shop);
        visit.setVisitor(visitor);
        visitRepository.save(visit);

        Visit createdVisit = visitRepository.findTopByOrderByVisitIdDesc();
        return removeSelfReference(createdVisit);
    }

    public List<OverlapViewModel> getAllOverLaps(Optional<LocalDate> localDate) {
        List<OverlapViewModel>vms = new ArrayList<>();
        List<Visit> visits = visitRepository.findAll();

        //1. loop through each visit
        //2. For the current visit, compare it with other elements in a nested for loop
        //3. Ignore self comparison
        //4. If comparator starts after target, or ends before target, append to list of vms
        for (int i = 0; i < visits.size(); i++) {
            Visit targetVisit = visits.get(i);
            LocalDateTime targetDateTimeIn = targetVisit.getDateTimeIn();
            LocalDateTime targetDateTimeOut = targetVisit.getDateTimeOut();
            OverlapViewModel vm = new OverlapViewModel(targetDateTimeIn, targetDateTimeOut);
            vm.visits.add(targetVisit);

            for (int j = 0; j < visits.size(); j++ ) {
                if (j == i)
                    continue;
                Visit comparatorVisit = visits.get(j);
                LocalDateTime comparatorDateTimeIn = comparatorVisit.getDateTimeIn();
                LocalDateTime comparatorDateTimeOut = comparatorVisit.getDateTimeOut();

                if (comparatorDateTimeIn == null || comparatorDateTimeOut == null
                        || targetDateTimeIn == null
                        || targetDateTimeOut == null)
                    continue;

                boolean isOverlap1 = (comparatorDateTimeIn.isAfter(targetDateTimeIn) && comparatorDateTimeOut.isBefore(targetDateTimeOut));
                boolean isOverlap2 = (comparatorDateTimeIn.isBefore(targetDateTimeIn) && comparatorDateTimeOut.isAfter(targetDateTimeOut));
                if (isOverlap1 || isOverlap2)
                    vm.visits.add(comparatorVisit);

            }//end of inner for loop
            //If the overlap viewModel has a visit other than the target self
            if (vm.visits.size() >= 2)
                vms.add(vm);
        }//end of outer for loop

        vms.forEach(vm -> {
            vm.visits = removeSelfReference(vm.visits);
        });
        return vms;
    }

    @Async  //Annotation can work for entity that is many to one, but not vice versa
    public CompletableFuture<Visit> getById(int visitId) {
        CompletableFuture<Visit> visitCf = visitRepository.getByVisitId(visitId);
        return visitCf.thenApply(visit -> removeSelfReference(visit));
    }

    protected Visitor createVisitor(String nric, String name) {
        Visitor visitor = new Visitor();
        visitor.setNric(nric);
        visitor.setName(name);
        visitorRepository.save(visitor);
        visitor = visitorRepository.findTopByOrderByVisitorId();
        return visitor;
    }

    protected List<Visit> removeSelfReference(List<Visit> visits) {
        for (Visit visit : visits) {
            if (visit == null)
                continue;
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
}
