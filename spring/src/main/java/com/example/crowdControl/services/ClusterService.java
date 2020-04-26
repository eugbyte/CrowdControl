package com.example.crowdControl.services;

import com.example.crowdControl.models.Shop;
import com.example.crowdControl.models.Visit;
import com.example.crowdControl.models.Visitor;
import com.example.crowdControl.repositories.VisitRepository;
import com.example.crowdControl.viewModels.ClusterViewModel;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ClusterService implements ICluster {

    @Autowired
    protected VisitRepository visitRepository;

    public List<ClusterViewModel> getAllClusters() {
        List<Visit> visits = visitRepository.findAll();
        return getClustersFromVisits(visits);
    }

    public List<ClusterViewModel> findClustersOfShop(int shopId) {
        List<Visit> visits = visitRepository.findByShop_shopId(shopId);
        List<ClusterViewModel> clusterVms = getClustersFromVisits(visits);
        return getDistinctClusters(clusterVms);
    }

    protected List<ClusterViewModel> getClustersFromVisits(List<Visit> visits) {
        List<ClusterViewModel>vms = new ArrayList<>();
        //1. loop through each visit
        //2. For the current visit, compare it with other elements in a nested for loop
        //3. Ignore self comparison
        //4. If comparator starts after target, or ends before target, append to list of vms
        for (int i = 0; i < visits.size(); i++) {
            Visit targetVisit = visits.get(i);
            LocalDateTime targetDateTimeIn = targetVisit.getDateTimeIn();
            LocalDateTime targetDateTimeOut = targetVisit.getDateTimeOut();
            ClusterViewModel vm = new ClusterViewModel(targetDateTimeIn, targetDateTimeOut);
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

                //The overlapping scenarios
                boolean isOverlap1 = (comparatorDateTimeIn.isAfter(targetDateTimeIn) && comparatorDateTimeIn.isBefore(targetDateTimeOut));
                boolean isOverlap2 = (comparatorDateTimeIn.isBefore(targetDateTimeIn) && comparatorDateTimeOut.isAfter(targetDateTimeOut));
                boolean isOverlap3 = (comparatorDateTimeIn.isBefore(targetDateTimeIn) && comparatorDateTimeOut.isAfter(targetDateTimeIn));
                if (isOverlap1 || isOverlap2 || isOverlap3)
                    vm.visits.add(comparatorVisit);

            }//end of inner for loop
            //If the overlap viewModel has a visit other than the target self
            if (vm.visits.size() >= 2)
                vms.add(vm);
        }//end of outer for loop

        vms.forEach(vm -> vm.visits = removeSelfReference(vm.visits));

        return vms;
    }

    //For example
    //1500 -> 1700
    //1600 -> 1800
    //There will be 2 overlap periods for each. The vm.visits will contain the same List<Visit>
    //You want to get 1600 -> 1700
    protected List<ClusterViewModel> getDistinctClusters(List<ClusterViewModel> vms) {
        List<ClusterViewModel> resultVms = new ArrayList<>();
        for (int i = 0; i < vms.size(); i++) {
            ClusterViewModel targetVm = vms.get(i);
            for (int j = 0; j < vms.size(); j++) {
                if (j == i)
                    continue;
                ClusterViewModel otherVm = vms.get(j);
                //equals method for ClusterViewModel has been overridden, which also affects contains() method
                //new equals method compares by List<int> visitIds
                if (!(targetVm.equals(otherVm)) || resultVms.contains(targetVm))
                    continue;
                resultVms.add(targetVm);
            }
        }
        resultVms = setNarrowestTimeWindow(resultVms);
        return resultVms;
    }

    protected  List<ClusterViewModel> setNarrowestTimeWindow(List<ClusterViewModel> resultVms) {
        resultVms.forEach(vm -> {
            List<Visit> visits = vm.visits;
            List<LocalDateTime> startTimes = visits.stream().map(visit -> visit.getDateTimeIn()).collect(Collectors.toList());
            LocalDateTime minStartTime = Collections.min(startTimes);

            List<LocalDateTime> endTimes = visits.stream().map(visit -> visit.getDateTimeOut()).collect(Collectors.toList());
            LocalDateTime minEndTime = Collections.min(endTimes);
            vm.dateTimeIn = minStartTime;
            vm.dateTimeOut = minEndTime;

        });
        return resultVms;
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
