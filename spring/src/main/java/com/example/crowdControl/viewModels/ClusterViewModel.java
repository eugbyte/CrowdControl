package com.example.crowdControl.viewModels;

import com.example.crowdControl.models.Visit;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClusterViewModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dateTimeIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dateTimeOut;

    public List<Visit> visits;

    public ClusterViewModel(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut) {
        this.dateTimeIn = dateTimeIn;
        this.dateTimeOut = dateTimeOut;
        this.visits = new ArrayList<Visit>();
    }

    public ClusterViewModel() {
        this.visits = new ArrayList<Visit>();
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof ClusterViewModel))
            return false;
        ClusterViewModel otherVm = (ClusterViewModel)obj;

        List<Integer> currentVisitIds = this.visits.stream()
                .map(visit -> visit.getVisitId()).collect(Collectors.toList());
        List<Integer> otherVisitIds = otherVm.visits.stream()
                .map(visit -> visit.getVisitId()).collect(Collectors.toList());

        return currentVisitIds.retainAll(otherVisitIds);
    }

}
