package com.example.crowdControl.services;

import com.example.crowdControl.models.Visitor;

import java.util.List;

public interface IVisitor {
    List<Visitor> findAllVisitors();
    Visitor findVisitorById(int visitorId);
}
