package com.example.crowdControl.viewModels;

import com.example.crowdControl.models.Visit;

import java.time.LocalDateTime;
import java.util.List;

public class OverlapViewModel {
    public LocalDateTime dateTimeIn;
    public LocalDateTime dateTimeOut;

    public List<Visit> visits;

    public OverlapViewModel(LocalDateTime dateTimeIn, LocalDateTime dateTimeOut, List<Visit> visits) {
        this.dateTimeIn = dateTimeIn;
        this.dateTimeOut = dateTimeOut;
        this.visits = visits;
    }

    public OverlapViewModel() { };
}
