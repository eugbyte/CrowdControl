package com.example.crowdControl.models;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int visitorId;

    @OneToMany(mappedBy = "visitor", fetch = FetchType.LAZY)
    private List<Visit> visits;

    private String name;
    private String nric;


}
