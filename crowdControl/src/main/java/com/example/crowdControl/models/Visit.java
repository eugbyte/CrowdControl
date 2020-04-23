package com.example.crowdControl.models;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int visitId;

    @ManyToOne
    private Visitor visitor;

    @ManyToOne
    private Shop shop;

    private Date DateTimeIn;
    private Date DateTimeOut;
}
