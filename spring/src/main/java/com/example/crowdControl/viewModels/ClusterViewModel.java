package com.example.crowdControl.viewModels;

import com.example.crowdControl.models.Visit;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
